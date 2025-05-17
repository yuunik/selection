package com.yuunik.selection.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    // 根据用户id查询用户所拥有的角色id
    List<Long> selectAllUserRoleIds(Long userId);

    // 根据用户id删除用户所拥有的角色
    void deleteUserRoleByUserId(Long userId);

    // 添加用户角色
    void insertUserRole(Long userId, Long addRoleId);
}
