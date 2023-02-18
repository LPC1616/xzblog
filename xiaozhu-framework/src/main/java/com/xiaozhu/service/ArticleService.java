package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AddArticleDto;
import com.xiaozhu.domain.dto.AdminArticleDto;
import com.xiaozhu.domain.dto.ArticleDto;
import com.xiaozhu.domain.entity.Article;
import com.xiaozhu.domain.vo.UpdateArticleVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto);

    ResponseResult getArticleById(Long id);

    ResponseResult updateArticle(AdminArticleDto articleDto);

    ResponseResult deleteArticle(Long id);
}
