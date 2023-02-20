package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhu.constants.SystemConstants;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.CategoryDto;
import com.xiaozhu.domain.entity.Article;
import com.xiaozhu.domain.entity.Category;
import com.xiaozhu.domain.vo.CategoryTwoVo;
import com.xiaozhu.domain.vo.CategoryVo;
import com.xiaozhu.domain.vo.PageVo;
import com.xiaozhu.enums.AppHttpCodeEnum;
import com.xiaozhu.mapper.CategoryMapper;
import com.xiaozhu.service.ArticleService;
import com.xiaozhu.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaozhu.service.CategoryService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-01-31 10:53:25
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());

        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult getArticleCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        //        1.根据文章分类名(模糊查询)和状态进行查询
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(categoryDto.getStatus()), Category::getStatus, categoryDto.getStatus());
        queryWrapper.like(StringUtils.hasText(categoryDto.getName()), Category::getName, categoryDto.getName());

        Page<Category> page = new Page<>();
        page(page, queryWrapper);

        List<Category> categories = page.getRecords();
        List<CategoryTwoVo> categoryTwoVos = BeanCopyUtils.copyBeanList(categories, CategoryTwoVo.class);
        PageVo pageVo = new PageVo(categoryTwoVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        //        1.首先根据分类名称查询要添加的友链是否存在
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Category::getName, categoryDto.getName());
        Category category = getOne(queryWrapper);
        if (!Objects.isNull(category)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CATEGORY_IS_EXIST);
        }
//        2.添加分类
//          2.1将CategoryDto对象转为Category对象
        Category addCategory = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(addCategory);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryOneById(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        if (!StringUtils.hasText(categoryDto.getName()) &&
                !StringUtils.hasText(categoryDto.getDescription()) &&
                !StringUtils.hasText(String.valueOf(categoryDto.getStatus()))) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        boolean result = removeById(id);
        if (result == false) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_CATEGORY_FAIL);
        }
        return ResponseResult.okResult();
    }


}
