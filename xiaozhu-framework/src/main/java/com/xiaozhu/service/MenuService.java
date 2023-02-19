package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.MenuDto;
import com.xiaozhu.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-02-15 10:58:46
 */
public interface MenuService extends IService<Menu> {


    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getMenuList(String status, String menuName);

    ResponseResult addMenu(MenuDto menuDto);

    ResponseResult getMenuById(Long id);

    ResponseResult updateMenu(MenuDto menuDto);

    ResponseResult deleteMenu(Long id);

    ResponseResult getMenuTree();

    ResponseResult roleMenuTreeselect(Long id);
}
