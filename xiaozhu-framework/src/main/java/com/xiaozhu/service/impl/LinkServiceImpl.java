package com.xiaozhu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhu.constants.SystemConstants;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AddLinkDto;
import com.xiaozhu.domain.dto.LinkDto;
import com.xiaozhu.domain.entity.Link;
import com.xiaozhu.domain.vo.LinkVo;
import com.xiaozhu.domain.vo.PageVo;
import com.xiaozhu.enums.AppHttpCodeEnum;
import com.xiaozhu.mapper.LinkMapper;
import com.xiaozhu.service.LinkService;
import com.xiaozhu.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-02-01 16:51:49
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(lambdaQueryWrapper);
        //转换vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getAllLinkByAdmin(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(linkDto.getStatus()), Link::getStatus, linkDto.getStatus());
        queryWrapper.like(StringUtils.hasText(linkDto.getName()), Link::getName, linkDto.getName());

//        2.分页查询
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

//        3.将当前页中的Link对象转换为LinkVo对象
        List<Link> links = page.getRecords();
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
//        4.将LinkVo对象转换为LinkAdminVo对象
        PageVo pageVo = new PageVo(linkVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        //        1.首先根据友链名称查询要添加的友链是否存在
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Link::getName, addLinkDto.getName());
        Link link = getOne(queryWrapper);
        if (!Objects.isNull(link)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.LINK_IS_EXIST);
        }
//        2.添加友链
//          2.1将AddLinkDto对象转为Link对象
        Link addLink = BeanCopyUtils.copyBean(addLinkDto, Link.class);
        save(addLink);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkOneById(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkDto linkDto) {
        //        1.判断LinkDto对象值是否为空
        if (!StringUtils.hasText(linkDto.getName()) ||
                !StringUtils.hasText(linkDto.getAddress()) ||
                !StringUtils.hasText(String.valueOf(linkDto.getStatus())) ||
                !StringUtils.hasText(linkDto.getLogo()) ||
                !StringUtils.hasText(linkDto.getDescription())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
//        2.将LinkVo对象转换为Link对象
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        boolean result = removeById(id);
        if (result == false) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_LINK_FAIL);
        }
        return ResponseResult.okResult();
    }
}
