package com.yuunik.selection.manager.service;

import com.yuunik.selection.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface SysRoleMenuService {
    Map<String, Object> getMenuByRoleId(Long roleId);

    void doAssignForMenu(AssginMenuDto assginMenuDto);
}
