package com.xiaozhu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.AddLinkDto;
import com.xiaozhu.domain.dto.LinkDto;
import com.xiaozhu.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-02-01 16:51:49
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getAllLinkByAdmin(Integer pageNum, Integer pageSize, LinkDto linkDto);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLinkOneById(Long id);

    ResponseResult updateLink(LinkDto linkDto);

    ResponseResult deleteLink(Long id);
}
