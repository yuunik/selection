package com.yuunik.selection.manager.mapper;

import com.github.pagehelper.PageInfo;
import com.yuunik.selection.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    // 分页查询角色信息
    List<SysRole> selectRoleList(String roleName);
}
