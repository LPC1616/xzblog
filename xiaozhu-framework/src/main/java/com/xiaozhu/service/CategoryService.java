package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-01-31 10:53:24
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
