package com.yuunik.selection.manager.controller;

import com.yuunik.selection.manager.service.SysRoleMenuService;
import com.yuunik.selection.model.dto.system.AssginMenuDto;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "角色菜单接口")
@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Operation(summary = "根据用户id查询所有菜单及用户所具有的菜单权限")
    @GetMapping("/getMenuByRoleId/{roleId}")
    public Result<Map<String, Object>> getMenuByRoleId(@PathVariable Long roleId) {
        Map<String, Object> menuInfo = sysRoleMenuService.getMenuByRoleId(roleId);
        return Result.build(menuInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "为角色分配权限")
    @PostMapping("/doAssignForMenu")
    public Result<Object> doAssignForMenu(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssignForMenu(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
