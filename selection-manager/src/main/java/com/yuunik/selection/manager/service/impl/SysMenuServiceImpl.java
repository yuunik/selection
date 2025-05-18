package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysMenuMapper;
import com.yuunik.selection.manager.mapper.SysRoleMapper;
import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.manager.utils.MenuHelper;
import com.yuunik.selection.model.entity.system.SysMenu;
import com.yuunik.selection.model.entity.system.SysRole;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.model.vo.system.SysMenuVo;
import com.yuunik.selection.util.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

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
        List<SysMenu> treeSysMenuList = MenuHelper.buildTree(sysMenuList);
        List<SysMenu> resultList = bulidDataList(treeSysMenuList, 1, SysMenu.class);
        return resultList;
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
    public Map<String, Object> getMenuOfUser() {
        // 获取当前的用户信息
        SysUser sysUser = AuthContextUtil.get();
        // 获取用户id
        Long userId = sysUser.getId();
        // 调用接口, 获取用户所具有的菜单权限
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuOfUser(userId);
        // 获取路由权限数组
        List<String> routeList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            routeList.add(sysMenu.getComponent());
        }
        // 构建SysMenuVo树形列表
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        // List<SysMenu> ===> List<SySMenuVo>
        List<SysMenuVo> sysMenuVoList = bulidDataList(sysMenuTreeList, 1, SysMenuVo.class);
        // 获取按钮权限数组
        List<String> buttonList = buildButtonList(sysMenuVoList);
        // 获取用户所具有的角色列表
        List<SysRole> sysRoleList = sysRoleMapper.selectRoleListOfUser(userId);
        // 获取用户所具有的角色名称列表
        List<String> roleList = new ArrayList<>();
        for (SysRole sysRole : sysRoleList) {
            roleList.add(sysRole.getRoleName());
        }
        // 根据返回数据, 封装返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("routeList", routeList);
        resultMap.put("buttonList", buttonList);
        resultMap.put("roleList", roleList);
        resultMap.put("userInfo", sysUser);
        return resultMap;
    }

    // 构建T列表 T 为 SysMenu 或 SysMenuVo
    public <T> List<T> bulidDataList(List<SysMenu> sysMenuList, Integer currLevel, Class<T> targetClass) {
        List<T> dataList = new ArrayList<>();
        SysMenuVo sysMenuVo = null;
        SysMenu sysMenuCp = null;
        // 遍历sysMenuList, 将其转换为SysMenuVo
        for (SysMenu sysMenu : sysMenuList) {
            if (targetClass.equals(SysMenuVo.class)) {
                sysMenuVo = new SysMenuVo();
                sysMenuVo.setTitle(sysMenu.getTitle());
                sysMenuVo.setName(sysMenu.getComponent());
                sysMenuVo.setLevel(currLevel);

                if (CollUtil.isNotEmpty(sysMenu.getChildren())) {
                    sysMenuVo.setChildren(bulidDataList(sysMenu.getChildren(), currLevel + 1, SysMenuVo.class));
                } else {
                    sysMenuVo.setChildren(List.of());
                }
                dataList.add(targetClass.cast(sysMenuVo));
            } else if (targetClass.equals(SysMenu.class)) {
                sysMenuCp = new SysMenu();
                BeanUtil.copyProperties(sysMenu, sysMenuCp);
                // 单独设置 level
                sysMenuCp.setLevel(currLevel);

                if (CollUtil.isNotEmpty((sysMenu.getChildren()))) {
                    sysMenuCp.setChildren(bulidDataList(sysMenu.getChildren(), currLevel + 1, SysMenu.class));
                } else {
                    sysMenuCp.setChildren(List.of());
                }
                dataList.add(targetClass.cast(sysMenuCp));
            }
        }
        return dataList;
    }

    // 获取按钮权限列表
    public List<String> buildButtonList(List<SysMenuVo> sysMenuVoList) {
        List<String> buttonList = new ArrayList<>();
        for (SysMenuVo sysMenuVo : sysMenuVoList) {
            if (sysMenuVo.getLevel().equals(3)) {
                buttonList.add(sysMenuVo.getName());
            }
            if (CollUtil.isNotEmpty(sysMenuVo.getChildren())) {
                buttonList.addAll(buildButtonList(sysMenuVo.getChildren()));
            }
        }
        return buttonList;
    }

    // 获取个人相关信息
    public Map<String, Object> getPersonalInfo(List<SysMenu> sysMenuList) {
        // 获取用户所具有的路由权限列表
        List<String> routeList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            // 获取每个路由
            routeList.add(sysMenu.getComponent());
        }
        // sysMenuList ===> sysMenuVoList
        List<SysMenuVo> sysMenuVoList = bulidDataList(sysMenuList, 1, SysMenuVo.class);
        // 获取用户所具有的按钮权限列表
        List<String> buttonList = buildButtonList(sysMenuVoList);
        // 获取用户所具有的角色列表
        List<SysRole> sysRoleList = sysRoleMapper.selectAllRoles();
        // 获取用户信息
        SysUser sysUser = AuthContextUtil.get();
        // 封装返回数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("routeList", routeList);
        resultMap.put("buttonList", buttonList);
        resultMap.put("userInfo", sysUser);
        return resultMap;
    }
}
