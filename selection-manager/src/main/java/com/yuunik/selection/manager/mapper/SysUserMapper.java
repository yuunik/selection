package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.dto.system.SysUserDto;
import com.yuunik.selection.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    // 根据用户名查询用户信息
    SysUser selectUserInfoByUserName(String userName);
    // 分页查询用户信息
    List<SysUser> pageUserList(SysUserDto sysUserDto);
    // 添加用户信息
    void insertUser(SysUser sysUser);
    // 删除用户信息
    void deleteUser(Integer id);
    // 修改用户信息
    void updateUser(SysUser sysUser);
}
