package com.yuunik.selection.manager.service;

import com.yuunik.selection.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> getMenuList();

    void addMenu(SysMenu sysMenu);

    void deleteMenu(Long id);

    void updateMenu(SysMenu sysMenu);
}
