package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.entity.ArticleTag;

import java.util.List;


/**
 * 文章标签关联表(ArticleTag)表服务接口
 *
 * @author makejava
 * @since 2023-02-17 17:09:01
 */
public interface ArticleTagService extends IService<ArticleTag> {

    List<Long> getTagList(Long id);
}
