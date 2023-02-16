package com.xiaozhu.service;

import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);


    ResponseResult logout();
}