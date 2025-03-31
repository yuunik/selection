package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yuunik.selection.manager.mapper.SysMenuMapper;
import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.manager.utils.MenuHelper;
import com.yuunik.selection.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    // 获取权限列表
    @Override
    public List<SysMenu> getMenuList() {
        // 调用接口, 获取所有权限
        List<SysMenu> sysMenuList = sysMenuMapper.selectAllMenu();
        if (CollUtil.isEmpty(sysMenuList)) {
            // 没有权限列表
            return List.of();
        }
        // 获取权限列表
        return MenuHelper.buildTree(sysMenuList);
    }
}
