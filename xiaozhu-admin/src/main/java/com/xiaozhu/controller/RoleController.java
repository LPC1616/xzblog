package com.xiaozhu.controller;

import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.RoleDto;
import com.xiaozhu.domain.vo.RoleStatusVo;
import com.xiaozhu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult getAllRoleByPage(Integer pageNum, Integer pageSize, RoleDto roleDto){
        return roleService.getAllRoleByPage(pageNum,pageSize,roleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeRoleStatus(@RequestBody RoleStatusVo roleStatusVo){
        return roleService.changeRoleStatus(roleStatusVo);
    }

    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto roleDto){
        return roleService.addRole(roleDto);
    }

    @GetMapping("/listAllRole")
    public ResponseResult getListAllRole(){
        return roleService.getListAllRole();
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleInfoById(@PathVariable Long id){
        return roleService.getRoleInfoById(id);
    }

    @PutMapping
    public ResponseResult updateRoleInfo(@RequestBody RoleDto roleDto){
        return roleService.updateRoleInfo(roleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }

}
