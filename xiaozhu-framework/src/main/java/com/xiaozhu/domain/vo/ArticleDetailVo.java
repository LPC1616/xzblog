package com.xiaozhu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名字

    private Long categoryId;

    private String categoryName;
    //缩略图
    private String thumbnail;

    private Long viewCount;

    private Date createTime;

    private String content;
}
