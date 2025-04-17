package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    // 获取所有权限
    List<SysMenu> selectAllMenu();

    // 删除权限
    void updateIsMenuDeletedById(Long id);

    // 新增权限
    void insertMenu(SysMenu sysMenu);

    // 修改权限
    void updateMenu(SysMenu sysMenu);

    // 根据id查询所拥有的权限数量
    int countMenuById(Long id);

    // 根据用户id查询所拥有的权限
    List<SysMenu> selectMenuOfUser(Long userId);
}
