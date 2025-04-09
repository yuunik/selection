package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysMenuMapper;
import com.yuunik.selection.manager.mapper.SysRoleMenuMapper;
import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.manager.utils.MenuHelper;
import com.yuunik.selection.model.entity.system.SysMenu;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    // 获取菜单列表
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

    // 添改菜单
    @Override
    public void addMenu(SysMenu sysMenu) {
        sysMenuMapper.insertMenu(sysMenu);
    }

    // 修改菜单
    @Override
    public void deleteMenu(Long id) {
        // 调用接口, 查询所需删除的菜单是否有子菜单
        int count = sysMenuMapper.countMenuById(id);
        if (count > 0) {
            // 有子菜单, 禁止删除
            throw new YuunikException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.updateIsMenuDeletedById(id);
    }

    // 删改菜单
    @Override
    public void updateMenu(SysMenu sysMenu) {
        sysMenuMapper.updateMenu(sysMenu);
    }
}
