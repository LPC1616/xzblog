package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.TagListDto;
import com.xiaozhu.domain.entity.Tag;
import com.xiaozhu.domain.vo.PageVo;
import com.xiaozhu.domain.vo.TagUpdateVo;
import com.xiaozhu.enums.AppHttpCodeEnum;
import com.xiaozhu.exception.SystemException;
import com.xiaozhu.mapper.TagMapper;
import com.xiaozhu.service.TagService;
import com.xiaozhu.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-14 21:55:18
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        //标签名字不能为空
        if (!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        //标签备注不能为空
        if (!StringUtils.hasText(tag.getRemark())){
            throw new SystemException(AppHttpCodeEnum.REMARK_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagInfo(Long id) {
        Tag tag = getById(id);
        TagUpdateVo vo = BeanCopyUtils.copyBean(tag, TagUpdateVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateTag(TagUpdateVo tagUpdateVo) {
        if (!StringUtils.hasText(tagUpdateVo.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(tagUpdateVo.getRemark())){
            throw new SystemException(AppHttpCodeEnum.REMARK_NOT_NULL);
        }
        Tag tag = BeanCopyUtils.copyBean(tagUpdateVo, Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagUpdateVo> listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getDelFlag, 0);
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(queryWrapper);
        List<TagUpdateVo> tagUpdateVos = BeanCopyUtils.copyBeanList(list, TagUpdateVo.class);
        return tagUpdateVos;
    }


}
