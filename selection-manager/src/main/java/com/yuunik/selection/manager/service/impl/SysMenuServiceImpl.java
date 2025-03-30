package com.yuunik.selection.manager.service.impl;

import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.model.entity.system.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    // 获取权限列表
    @Override
    public List<SysMenu> getMenuList() {
        return List.of();
    }
}
