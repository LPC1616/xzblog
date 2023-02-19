package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.entity.RoleMenu;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-02-18 21:36:17
 */
public interface RoleMenuService extends IService<RoleMenu> {

    List<Long> getRoleMenuIdsById(Long id);
}
