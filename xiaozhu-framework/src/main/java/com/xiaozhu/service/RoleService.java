package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.RoleDto;
import com.xiaozhu.domain.entity.Role;
import com.xiaozhu.domain.vo.RoleStatusVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-02-15 10:59:15
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getAllRoleByPage(Integer pageNum, Integer pageSize, RoleDto roleDto);

    ResponseResult changeRoleStatus(RoleStatusVo roleStatusVo);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult getListAllRole();

    ResponseResult getRoleInfoById(Long id);

    ResponseResult updateRoleInfo(RoleDto roleDto);

    ResponseResult deleteRole(Long id);
}
