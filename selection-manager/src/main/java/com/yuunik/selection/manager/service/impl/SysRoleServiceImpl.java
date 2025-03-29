package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysRoleMapper;
import com.yuunik.selection.manager.mapper.SysUserRoleMapper;
import com.yuunik.selection.manager.service.SysRoleService;
import com.yuunik.selection.model.dto.system.SysRoleDto;
import com.yuunik.selection.model.entity.system.SysRole;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    // 分页获取用户信息
    @Override
    public PageInfo<SysRole> pageRoleList(Integer current, Integer limit, SysRoleDto sysRoleDto) {
        // 设置分页参数
        PageHelper.startPage(current, limit);
        // 获取角色搜索条件
        String roleName = sysRoleDto.getRoleName();
        // 分页查询角色信息
        List<SysRole> sysRoleList = sysRoleMapper.selectRoleList(roleName);
        // 根据响应信息, 封装数据
        return new PageInfo<>(sysRoleList);
    }

    // 添加角色
    @Override
    public void addRole(SysRole sysRole) {
        sysRoleMapper.addRole(sysRole);
    }

    // 删除角色
    @Override
    public void deleteRole(Long id) {
        sysRoleMapper.deleteRole(id);
    }

    // 修改角色信息
    @Override
    public void updateRole(SysRole sysRole) {
        sysRoleMapper.updateRole(sysRole);
    }

    // 查询所有角色及用户所拥有的角色信息
    @Override
    public Map<String, Object> getRoleList(Long userId) {
        // 查询所有角色信息
        List<SysRole> sysRoleList = sysRoleMapper.selectAllRoles();
        // 查询用户所拥有的角色信息
        List<Long> userRoleIdList = sysUserRoleMapper.selectAllUserRoleIds(userId);
        // 封装数据
        Map<String, Object> result = new HashMap<>();
        result.put("sysRoleList", sysRoleList);
        result.put("userRoleIdList", userRoleIdList);
        return result;
    }
}
