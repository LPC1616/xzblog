package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.CategoryDto;
import com.xiaozhu.domain.entity.Category;
import com.xiaozhu.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-01-31 10:53:24
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult getArticleCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult getCategoryOneById(Long id);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult deleteCategory(Long id);
}
