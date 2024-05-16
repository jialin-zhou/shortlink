package com.nageoffer.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDO;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用戶接口实现层
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
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
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
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
        return userRegisterCachePenetrationBloomFilter.contains(username);

    }


}
