package com.xiaozhu.service;

import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
