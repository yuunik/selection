package com.yuunik.selection.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuunik.selection.manager.mapper.SysRoleMapper;
import com.yuunik.selection.manager.service.SysRoleService;
import com.yuunik.selection.model.dto.system.SysRoleDto;
import com.yuunik.selection.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

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
}
