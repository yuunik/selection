package com.yuunik.selection.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yuunik.selection.manager.service.SysUserService;
import com.yuunik.selection.model.dto.system.SysUserDto;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息接口")
@RestController
@RequestMapping("/admin/system/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "分页查询用户信息")
    @PostMapping("/pageUserList/{current}/{limit}")
    public Result<PageInfo<SysUser>> pageUserList(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody SysUserDto sysUserDto) {
        PageInfo<SysUser> pageUserList = sysUserService.pageUserList(current, limit, sysUserDto);
        return Result.build(pageUserList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加用户信息")
    @PostMapping("/addUser")
    public Result<Object> addUser(@RequestBody SysUser sysUser) {
        sysUserService.addUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除用户信息")
    @DeleteMapping("/deleteUser/{id}")
    public Result<Object> deleteUser(@PathVariable Integer id) {
        sysUserService.deleteUser(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改用户信息")
    @PutMapping("/updateUser")
    public Result<Object> updateUser(@RequestBody SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
