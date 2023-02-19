package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.RoleDto;
import com.xiaozhu.domain.entity.Role;
import com.xiaozhu.domain.entity.RoleMenu;
import com.xiaozhu.domain.vo.PageVo;
import com.xiaozhu.domain.vo.RoleStatusVo;
import com.xiaozhu.domain.vo.RoleVo;
import com.xiaozhu.enums.AppHttpCodeEnum;
import com.xiaozhu.mapper.RoleMapper;
import com.xiaozhu.service.RoleMenuService;
import com.xiaozhu.service.RoleService;
import com.xiaozhu.utils.BeanCopyUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-02-15 10:59:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult getAllRoleByPage(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        //        1.根据友链名(模糊查询)和状态进行查询
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(roleDto.getStatus()), Role::getStatus, roleDto.getStatus());
        queryWrapper.like(StringUtils.hasText(roleDto.getRoleName()), Role::getRoleName, roleDto.getRoleName());

//        2.分页查询
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

//        3.将当前页中的Role对象转换为RoleVo对象
        List<Role> roles = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
//        4.将LinkVo对象转换为LinkAdminVo对象
        PageVo pageVos = new PageVo(roleVos, page.getTotal());
        return ResponseResult.okResult(pageVos);
    }

    @Override
    public ResponseResult changeRoleStatus(RoleStatusVo roleStatusVo) {
        LambdaUpdateWrapper<Role> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Role::getId, Integer.parseInt(roleStatusVo.getRoleId()))
                .set(Role::getStatus, roleStatusVo.getStatus());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        //        1.根据角色名判断当前角色是否存在
        if (!this.judgeRole(roleDto.getRoleName())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLE_INFO_EXIST);
        }

//        1.根据权限名判断当前角色是否存在
        if (!this.judgeRoleKey(roleDto.getRoleKey())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.ROLEKEY_INFO_EXIST);
        }

//        2.获取到当前角色的菜单权限列表
        List<String> menuIds = roleDto.getMenuIds();

//        3.将RoleDto对象转换为Role对象
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);

//        4.根据角色名获取到当前角色
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getRoleName, role.getRoleName());
        Role getRole = getOne(queryWrapper);

//        5.遍历menuIds，添加到sys_role_menu表中
        menuIds.stream()
                .map(menuId -> roleMenuService.save
                        (new RoleMenu(getRole.getId(), Long.valueOf(menuId))));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getListAllRole() {
        List<Role> roles = roleMapper.selectList(null);
        return ResponseResult.okResult(roles);
    }

    @Override
    public ResponseResult getRoleInfoById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRoleInfo(RoleDto roleDto) {
        //        1.判断LinkDto对象值是否为空
        if (!StringUtils.hasText(roleDto.getRoleName()) ||
                !StringUtils.hasText(roleDto.getRoleKey()) ||
                !StringUtils.hasText(String.valueOf(roleDto.getStatus())) ||
                !StringUtils.hasText(roleDto.getRemark()) ||
                !StringUtils.hasText(String.valueOf(roleDto.getRoleSort()))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }

//        2.获取到当前角色的菜单权限列表
        List<Long> menuIds = roleDto.getMenuIds().stream().map(menuId -> Long.valueOf(menuId)).collect(Collectors.toList());

//        3.将RoleDto对象转换为Role对象
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        updateById(role);

//        4.查询当前角色的menuIds列表
        List<Long> roleMenuIdsById = roleMenuService.getRoleMenuIdsById(role.getId());
//        5.根据roleId移除sys_role_menu表中的数据
        roleMenuService.removeById(role.getId());
//          2.2遍历修改后的用户的menuIds列表，添加到sys_role_menu表中
        for (Long menuId : menuIds) {
            roleMenuService.save(new RoleMenu(role.getId(), menuId));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        roleMenuService.removeById(id);
        removeById(id);
        return ResponseResult.okResult();
    }

    public boolean judgeRole(String roleName) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Role::getRoleName, roleName);
        Role role = roleService.getOne(queryWrapper);
        if (Objects.isNull(role)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean judgeRoleKey(String roleKey) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Role::getRoleKey, roleKey);
        Role role = roleService.getOne(queryWrapper);
        if (Objects.isNull(role)) {
            return true;
        } else {
            return false;
        }
    }
}
