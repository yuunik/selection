package com.yuunik.selection.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yuunik.selection.manager.service.SysRoleService;
import com.yuunik.selection.model.dto.system.SysRoleDto;
import com.yuunik.selection.model.entity.system.SysRole;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户角色接口")
@RestController
@RequestMapping("/admin/system/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "分页查询用户信息")
    @PostMapping("/pageRoleList/{current}/{limit}")
    public Result<PageInfo<SysRole>> pageRoleList(@PathVariable Integer current,
                                                  @PathVariable Integer limit,
                                                  @RequestBody SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo = sysRoleService.pageRoleList(current, limit, sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
