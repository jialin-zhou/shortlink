package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.GroupService;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;

/**
 * 用戶接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final GroupService groupService;
    /**
     * 根据用户名获取用户信息。
     *
     * @param username 用户名，用于查询用户信息。
     * @return UserRespDTO 用户响应数据传输对象，包含查询到的用户信息。
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        // 构建查询条件，查询指定用户名的用户信息
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL_ERROR);
        }
        log.info("查询到的用户信息：{}", userDO);

        // 创建用户响应DTO，并将查询到的用户信息拷贝到DTO中
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);

        return result;
    }

    /**
     * 检查数据库中是否存在指定的用户名。
     *
     * @param username 待检查的用户名。
     * @return 用户名存在 返回False，用户名不存在，返回True
     */
    @Override
    public Boolean hasUsername(String username) {
        // 方式一：使用MyBatis-Plus的查询条件构建器构建查询条件，然后调用mapper的selectOne方法进行查询
        /**
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        return userDO == null;
         */
        // 方式二：使用Redis的布隆过滤器进行查询
        return !userRegisterCachePenetrationBloomFilter.contains(username);

    }

    /**
     * 用户注册
     * @param requestParam 注册用户请求参数
     */
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (!hasUsername(requestParam.getUsername())) {
            throw new ClientException(UserErrorCodeEnum.USER_EXIST_ERROR);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            if (lock.tryLock()) {
                try {
                    int insserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                    if (insserted < 1) {
                        throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                    }
                } catch (DuplicateKeyException ex) {
                    throw new ClientException(UserErrorCodeEnum.USER_EXIST_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                groupService.saveGroup("默认分组");
                return;
            }
            throw new ClientException(UserErrorCodeEnum.USER_REGISTER_LOCK_ERROR);
        } finally {
            lock.unlock();
        }
    }


    /**
     * 根据用户名修改用户信息
     *
     * @param requestParam 信息更新用户请求参数
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
        // TODO 验证当前用户是否为登录用户
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    /**
     * 用户登录
     *
     * @param requestParam 用户登录请求参数
     * @return
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL_ERROR);
        }
        Boolean isLogin = stringRedisTemplate.hasKey("login:" + requestParam.getUsername());
        if (isLogin != null && isLogin){
            throw new ClientException(UserErrorCodeEnum.USER_ALREADY_LOGIN_ERROR);
        }
        /**
         * Hash
         * Key: login_用户名
         * Value:
         *  Key: 用户token
         *  Val: JSON 字符串 UserDO
         */
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put("login:" + requestParam.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire("login:" + requestParam.getUsername(), 30L, TimeUnit.DAYS);
        return new UserLoginRespDTO(uuid);
    }

    /**
     * 检查用户是否登录
     * @param token 用户登录token
     * @return
     */
    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login:" + username, token) != null;
    }

    /**
     * 用户退出登录
     * @param username 用户名
     * @param token 用户名对应的uuid Token
     */
    @Override
    public void logout(String username, String token) {
        if (checkLogin(username, token)) {
            stringRedisTemplate.delete("login:" + username);
            return;
        }
        throw new ClientException(UserErrorCodeEnum.USER_NOT_LOGIN_ERROR);
    }
}
