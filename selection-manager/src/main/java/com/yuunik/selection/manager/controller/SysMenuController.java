package com.yuunik.selection.manager.controller;

import com.yuunik.selection.manager.service.SysMenuService;
import com.yuunik.selection.model.entity.system.SysMenu;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "菜单信息接口")
@RequestMapping("/admin/system/menu")
@RestController
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @Operation(summary = "获取权限列表")
    @GetMapping("/getMenuList")
    public Result<List<SysMenu>> getMenuList() {
        List<SysMenu> menuList = sysMenuService.getMenuList();
        return Result.build(menuList, ResultCodeEnum.SUCCESS);
    }

}
