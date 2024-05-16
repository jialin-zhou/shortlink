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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用戶接口实现层
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
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

}
