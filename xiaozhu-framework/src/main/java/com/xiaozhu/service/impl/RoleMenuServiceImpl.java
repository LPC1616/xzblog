package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhu.domain.entity.RoleMenu;
import com.xiaozhu.mapper.RoleMenuMapper;
import com.xiaozhu.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-02-18 21:36:17
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public List<Long> getRoleMenuIdsById(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, id);
        List<Long> collect = list(queryWrapper).stream().map(roleMenu -> roleMenu.getRoleId()).collect(Collectors.toList());
        return collect;
    }
}
