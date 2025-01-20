package com.yuunik.selection.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YuunikException extends RuntimeException {
    // 状态码
    private Integer code;
    // 异常信息
    private String msg;
}
