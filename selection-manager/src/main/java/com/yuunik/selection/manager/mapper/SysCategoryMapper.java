package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysCategoryMapper {
    // 查询所有分类
    List<Category> selectCategoryList(String id);

    // 根据id查询分类
    int countCategoryById(Long id);
}
