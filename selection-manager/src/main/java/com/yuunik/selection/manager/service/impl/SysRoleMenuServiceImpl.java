package com.yuunik.selection.manager.service.impl;

import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysRoleMenuMapper;
import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.manager.service.SysRoleMenuService;
import com.yuunik.selection.model.dto.system.AssginMenuDto;
import com.yuunik.selection.model.entity.system.SysMenu;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    // 根据用户id查询所有菜单及用户所具有的菜单权限
    @Override
    public Map<String, Object> getMenuByRoleId(Long roleId) {
        // 获取所有菜单
        List<SysMenu> sysMenuList = sysMenuService.getMenuList();
        // 获取用户所具有的菜单权限
        List<Long> roleMenuIdList = sysRoleMenuMapper.getRoleMenuIdList(roleId);
        // 根据响应参数, 封装数据
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("sysMenuList", sysMenuList);
        resultMap.put("roleMenuIdList", roleMenuIdList);
        return resultMap;
    }

    // 给角色分配菜单
    @Transactional
    @Override
    public void doAssignForMenu(AssginMenuDto assginMenuDto) {
        // 获取用户 id
        Long roleId = assginMenuDto.getRoleId();
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        try {
            // 删除已有菜单
            sysRoleMenuMapper.deleteRoleMenuByRoleId(roleId);
            // 分配菜单权限
            sysRoleMenuMapper.assignRoleMenu(assginMenuDto);
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出异常
            throw new YuunikException(ResultCodeEnum.SQL_ERROR);
        }
    }
}
