package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.entity.UserRole;

import java.util.List;


/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2023-02-19 16:42:36
 */
public interface UserRoleService extends IService<UserRole> {

    List<Long> getUserRoleById(Long id);
}
