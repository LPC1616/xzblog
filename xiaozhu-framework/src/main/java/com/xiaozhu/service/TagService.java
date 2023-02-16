package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.TagListDto;
import com.xiaozhu.domain.entity.Tag;
import com.xiaozhu.domain.vo.PageVo;
import com.xiaozhu.domain.vo.TagUpdateVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-02-14 21:55:17
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);


    ResponseResult addTag(Tag tag);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagInfo(Long id);

    ResponseResult updateTag(TagUpdateVo tagUpdateVo);
}
