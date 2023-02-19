package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AdminUserDto;
import com.xiaozhu.domain.dto.UserDto;
import com.xiaozhu.domain.entity.User;
import com.xiaozhu.domain.vo.UpdateUserInfoRoleIdVo;
import com.xiaozhu.domain.vo.UserStatusVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-06 16:21:57
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult getAllUserByPage(Integer pageNum, Integer pageSize, UserDto userDto);

    ResponseResult addUser(AdminUserDto adminUserDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserInfoById(Long id);

    ResponseResult updateUserStatus(UserStatusVo userStatusVo);

    ResponseResult adminUpdateUserInfo(UpdateUserInfoRoleIdVo updateUserInfoRoleIdVo);
}
