package com.yuunik.selection.manager.service;

import com.github.pagehelper.PageInfo;
import com.yuunik.selection.model.dto.system.SysRoleDto;
import com.yuunik.selection.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> pageRoleList(Integer current, Integer limit, SysRoleDto sysRoleDto);

    void addRole(SysRole sysRole);

    void deleteRole(Long id);

    void updateRole(SysRole sysRole);

    Map<String, Object> getRoleList(Long userId);
}
