package com.yuunik.selection.manager.controller;

import com.yuunik.selection.manager.service.SysUserService;
import com.yuunik.selection.manager.service.ValidateCodeService;
import com.yuunik.selection.model.dto.system.LoginDto;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.model.vo.system.LoginVo;
import com.yuunik.selection.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户登录接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

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
}
