package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysMenuMapper;
import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.manager.utils.MenuHelper;
import com.yuunik.selection.model.entity.system.SysMenu;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.model.vo.system.SysMenuVo;
import com.yuunik.selection.util.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // 获取用户所具有的菜单权限
    @Override
    public List<SysMenuVo> getMenuOfUser() {
        // 获取当前的用户信息
        SysUser sysUser = AuthContextUtil.get();
        // 获取用户id
        Long userId = sysUser.getId();
        // 调用接口, 获取用户所具有的菜单权限
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuOfUser(userId);
        // List<SySMenuVo> ===> List<SysMenu>
        List<SysMenuVo> sysMenuVoList = bulidSysMenuVoList(sysMenuList);
        return sysMenuVoList;
    }

    // 构建SysMenuVo列表
    public List<SysMenuVo> bulidSysMenuVoList(List<SysMenu> sysMenuList) {
        List<SysMenuVo> sysMenuVoList = new ArrayList<>();
        SysMenuVo sysMenuVo = null;
        // 遍历sysMenuList, 将其转换为SysMenuVo
        for (SysMenu sysMenu : sysMenuList) {
            sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            if (CollUtil.isNotEmpty(sysMenu.getChildren())) {
                sysMenuVo.setChildren(bulidSysMenuVoList(sysMenu.getChildren()));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
