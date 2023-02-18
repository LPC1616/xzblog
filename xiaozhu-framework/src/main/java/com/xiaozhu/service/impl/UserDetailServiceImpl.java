package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaozhu.constants.SystemConstants;
import com.xiaozhu.domain.entity.LoginUser;
import com.xiaozhu.domain.entity.User;
import com.xiaozhu.mapper.MenuMapper;
import com.xiaozhu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        //如果没查到，则抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getType().equals(SystemConstants.ADMIN)){
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user, perms);
        }
        return new LoginUser(user,null);
    }
}
