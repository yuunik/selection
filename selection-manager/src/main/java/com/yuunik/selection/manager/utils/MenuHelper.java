package com.yuunik.selection.manager.utils;

import cn.hutool.core.util.ObjectUtil;
import com.yuunik.selection.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    // 查找根节点
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> menuList = new ArrayList<>();
        SysMenu root = null;
        for (SysMenu sysMenu : sysMenuList) {
            if (sysMenu.getParentId() == 0) {
                // 获取到父节点, 递归查找子节点
                root = findChildren(sysMenu, sysMenuList);
                menuList.add(root);
            }
        }
        return menuList;
    }

    // 递归查找子节点
    private static SysMenu findChildren(SysMenu rootMenu, List<SysMenu> sysMenuList) {
        List<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : sysMenuList) {
            if (ObjectUtil.equals(menu.getParentId(), rootMenu.getId())) {
                // 递归查找子节点
                children.add(findChildren(menu, sysMenuList));
            }
        }
        // 设置子节点
        rootMenu.setChildren(children);
        return rootMenu;
    }
}
