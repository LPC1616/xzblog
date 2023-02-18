package com.xiaozhu.controller;

import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AddArticleDto;
import com.xiaozhu.domain.dto.AdminArticleDto;
import com.xiaozhu.domain.dto.ArticleDto;
import com.xiaozhu.domain.vo.UpdateArticleVo;
import com.xiaozhu.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("/list")
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleDto articleDto){
        return articleService.getArticleList(pageNum, pageSize, articleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleById(@PathVariable("id") Long id){
        return articleService.getArticleById(id);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestBody AdminArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }

}