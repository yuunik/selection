package com.yuunik.selection.manager.controller;

import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.manager.service.SysUserService;
import com.yuunik.selection.manager.service.ValidateCodeService;
import com.yuunik.selection.model.dto.system.LoginDto;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.model.vo.system.LoginVo;
import com.yuunik.selection.model.vo.system.SysMenuVo;
import com.yuunik.selection.model.vo.system.ValidateCodeVo;
import com.yuunik.selection.util.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "用户登录接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysMenuService sysMenuService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "生成验证码")
    @GetMapping("/getValidateCode")
    public Result<ValidateCodeVo> getValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.getValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo() {
        // 从thread local中获取用户信息
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户安全退出")
    @GetMapping("/logout")
    public Result<Object> logout(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring("Bearer ".length());
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "获取用户所具有的菜单权限")
    @GetMapping("/getMenuOfUser")
    public Result<List<SysMenuVo>> getMenuOfUser() {
        List<SysMenuVo> sysMenuVoList = sysMenuService.getMenuOfUser();
        return Result.build(sysMenuVoList, ResultCodeEnum.SUCCESS);
    }
}
