package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    // 分页查询角色信息
    List<SysRole> selectRoleList(String roleName);

    // 添加角色信息
    void addRole(SysRole sysRole);

    // 删除角色信息
    void deleteRole(Long id);

    // 修改角色信息
    void updateRole(SysRole sysRole);

    // 查询所有角色信息
    List<SysRole> selectAllRoles();
}
