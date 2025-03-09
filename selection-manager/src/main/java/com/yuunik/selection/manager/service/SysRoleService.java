package com.yuunik.selection.manager.service;

import com.github.pagehelper.PageInfo;
import com.yuunik.selection.model.dto.system.SysRoleDto;
import com.yuunik.selection.model.entity.system.SysRole;

public interface SysRoleService {
    PageInfo<SysRole> pageRoleList(Integer current, Integer limit, SysRoleDto sysRoleDto);
}
