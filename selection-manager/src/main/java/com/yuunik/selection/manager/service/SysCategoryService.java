package com.yuunik.selection.manager.service;

import com.yuunik.selection.model.entity.product.Category;

import java.util.List;

public interface SysCategoryService {
    List<Category> getCategoryList(String id);
}
