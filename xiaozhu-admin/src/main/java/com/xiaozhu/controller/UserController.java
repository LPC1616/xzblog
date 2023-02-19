package com.xiaozhu.controller;

import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AdminUserDto;
import com.xiaozhu.domain.dto.RoleDto;
import com.xiaozhu.domain.dto.UserDto;
import com.xiaozhu.domain.vo.UpdateUserInfoRoleIdVo;
import com.xiaozhu.domain.vo.UserStatusVo;
import com.xiaozhu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult getAllUserByPage(Integer pageNum, Integer pageSize, UserDto userDto){
        return userService.getAllUserByPage(pageNum,pageSize,userDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody AdminUserDto adminUserDto){
        return userService.addUser(adminUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUserInfoById(@PathVariable Long id){
        return userService.getUserInfoById(id);
    }

    @PutMapping("/changeStatus")
    public ResponseResult updateUserStatus(@RequestBody UserStatusVo userStatusVo){
        return userService.updateUserStatus(userStatusVo);
    }

    @PutMapping
    public ResponseResult updateUserInfo(@RequestBody UpdateUserInfoRoleIdVo updateUserInfoRoleIdVo){
        return userService.adminUpdateUserInfo(updateUserInfoRoleIdVo);
    }
}
