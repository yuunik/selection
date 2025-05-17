package com.yuunik.selection.manager.service;

import com.yuunik.selection.model.entity.system.SysMenu;
import com.yuunik.selection.model.vo.system.SysMenuVo;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<SysMenuVo> getMenuList();

    void addMenu(SysMenu sysMenu);

    void deleteMenu(Long id);

    void updateMenu(SysMenu sysMenu);

    Map<String, Object> getMenuOfUser();
}
