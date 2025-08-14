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

    @Override
    public List<Category> getCategoryList(String id) {
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
}
