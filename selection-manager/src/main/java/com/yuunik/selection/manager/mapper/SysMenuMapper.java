package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    // 获取所有权限
    List<SysMenu> selectAllMenu();
}
