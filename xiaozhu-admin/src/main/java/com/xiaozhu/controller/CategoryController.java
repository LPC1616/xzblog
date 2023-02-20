package com.xiaozhu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.xiaozhu.domain.ResponseResult;
import com.xiaozhu.domain.dto.CategoryDto;
import com.xiaozhu.domain.entity.Category;
import com.xiaozhu.domain.vo.CategoryVo;
import com.xiaozhu.domain.vo.ExcelCategoryVo;
import com.xiaozhu.enums.AppHttpCodeEnum;
import com.xiaozhu.service.CategoryService;
import com.xiaozhu.utils.BeanCopyUtils;
import com.xiaozhu.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询文章类别列表
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    @GetMapping("/list")
    public ResponseResult getArticleCategoryList(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        return categoryService.getArticleCategoryList(pageNum, pageSize, categoryDto);
    }

    /**
     * 添加类别
     *
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    /**
     * 通过id查询分类
     * @param id id
     * @return {@link ResponseResult}
     */
    @GetMapping("/{id}")
    public ResponseResult getCategoryOneById(@PathVariable Long id){
        return categoryService.getCategoryOneById(id);
    }

    /**
     * 修改文章分类信息
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }

    /**
     * 删除文章分类
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }




    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    @PreAuthorize("@ps.hasPerms('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}