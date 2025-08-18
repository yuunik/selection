package com.yuunik.selection.manager.controller;

import com.yuunik.selection.manager.service.SysCategoryService;
import com.yuunik.selection.model.entity.product.Category;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "商品分类管理")
@RestController
@RequestMapping("/admin/system/category")
public class SysCategoryController {
    @Autowired
    private SysCategoryService sysCategoryService;

    @Operation(summary = "获取分类列表 (供 element-plus 组件库使用)")
    @GetMapping("/getCategoryListForElementPlus/{id}")
    public Result<List<Category>> getCategoryListForElementPlus(@PathVariable String id) {
        List<Category> categoryList = sysCategoryService.getCategoryListForElementPlus(id);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取分类列表 (供 ant design 组件库使用)")
    @GetMapping("/getCategoryListForAntDesign")
    public Result<List<Category>> getCategoryListForAntDesign() {
        List<Category> categoryList = sysCategoryService.getCategoryListForAntDesign();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
