package com.yuunik.selection.common.exception;

import com.yuunik.selection.model.vo.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 捕获自定义异常
    @ExceptionHandler(YuunikException.class)
    @ResponseBody
    public Result handlerException(YuunikException e) {
        // 输出异常
        e.printStackTrace();
        return Result.build(new HashMap(), e.getResultCodeEnum());
    }
}
