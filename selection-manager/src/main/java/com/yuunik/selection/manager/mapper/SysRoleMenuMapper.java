package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    // 根据用户id查询用户所具有的菜单权限id
    List<Long> getRoleMenuIdList(Long roleId);

    // 根据角色id删除角色所具有的菜单权限
    void deleteRoleMenuByRoleId(Long roleId);

    // 为角色分配菜单权限
    void assignRoleMenu(AssginMenuDto assginMenuDto);
}
