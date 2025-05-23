package com.yuunik.selection.model.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "系统菜单响应结果实体类")
public class SysMenuVo {

    @Schema(description = "系统菜单标题")
    private String title;

    @Schema(description = "系统菜单名称")
    private String name;

    @Schema(description = "层级关系")
    private Integer level;

    @Schema(description = "系统菜单子菜单列表")
    private List<SysMenuVo> children;

}