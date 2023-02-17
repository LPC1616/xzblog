package com.xiaozhu.domain.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2023-02-17 17:09:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("xz_article_tag")
public class ArticleTag implements Serializable{
    //文章id@TableId
    @TableId(type = IdType.AUTO)
    private Long articleId;
    //标签id@TableId
    private Long tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



}
