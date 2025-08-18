package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysCategoryMapper;
import com.yuunik.selection.manager.service.SysCategoryService;
import com.yuunik.selection.model.entity.product.Category;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysCategoryServiceImpl implements SysCategoryService {
    @Autowired
    private SysCategoryMapper sysCategoryMapper;

    // 获取分类列表 (供 element-plus 组件库使用)
    @Override
    public List<Category> getCategoryListForElementPlus(String id) {
        // 获取分类列表
        List<Category> categoryList = sysCategoryMapper.selectCategoryList(id);

        if (CollectionUtil.isNotEmpty(categoryList)) {
            // 遍历 categoryList, 为每个 category 添加 hasChildren 属性
            categoryList.forEach(category -> {
                int count = sysCategoryMapper.countCategoryById(category.getId());
                category.setHasChildren(count > 0);
            });
            return categoryList;
        } else {
            throw new YuunikException(ResultCodeEnum.DATA_ERROR);
        }
    }

    // 获取分类列表 (供 ant design 组件库使用)
    @Override
    public List<Category> getCategoryListForAntDesign() {
        // 获取一级分类列表
        List<Category> categoryList = sysCategoryMapper.selectCategoryList("0");
        // 遍历一级分类列表
        for (Category category : categoryList) {
            List<Category> children = getChildCategoryList(category);
            // 设置是否有子分类
            category.setHasChildren(CollectionUtil.isNotEmpty(children));
            // 获取所属的子分类列表
            category.setChildren(children);
        }

        return categoryList;
    }

    // 获取子分类列表
    public List<Category> getChildCategoryList(Category category) {
        // 子分类列表
        List<Category> subCategoryList = sysCategoryMapper.selectCategoryList(category.getId().toString());
        // 检查获取的子分类列表是否为空
        if (CollectionUtil.isNotEmpty(subCategoryList)) {
            // 遍历子分类列表, 获取子分类列表的子分类列表
            for (Category subCategory : subCategoryList) {
                List<Category> children = getChildCategoryList(subCategory);
                // 设置是否有子分类
                subCategory.setHasChildren(!children.isEmpty());
                // 设置子分类列表
                subCategory.setChildren(getChildCategoryList(subCategory));
            }
            // 返回子分类列表
            return subCategoryList;
        }
        // 返回空列表
        return List.of();
    }
}
