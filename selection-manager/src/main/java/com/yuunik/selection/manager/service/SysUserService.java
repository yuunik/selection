package com.yuunik.selection.manager.service;

import com.yuunik.selection.model.dto.system.LoginDto;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);
}
