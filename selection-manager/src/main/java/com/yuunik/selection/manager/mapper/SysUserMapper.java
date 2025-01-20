package com.yuunik.selection.manager.mapper;

import com.yuunik.selection.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    // 根据用户名查询用户信息
    SysUser selectUserInfoByUserName(String userName);
}
