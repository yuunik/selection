package com.yuunik.selection.common.exception;

import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
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

    // 捕获sql语句异常错误
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public Result handlerException(SQLException e) {
        // 输出异常
        e.printStackTrace();
        return Result.build(new HashMap(), ResultCodeEnum.SQL_ERROR);
    }
}
