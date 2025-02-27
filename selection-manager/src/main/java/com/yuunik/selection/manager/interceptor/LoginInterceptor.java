package com.yuunik.selection.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.common.Result;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.util.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 拦截器，在请求处理完成后进行调用（Controller方法处理之后）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除 ThreadLocal 中的数据
        AuthContextUtil.remove();
    }

    // 拦截器，在请求处理之后进行调用（Controller方法处理之后，视图渲染之前）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 拦截器，在请求处理之前进行调用（Controller方法处理之前）
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求预处理, 则直接放行
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        // 获取请求的 Authorization, 不存在则返回未登录
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isEmpty(authorization)) {
            // 返回未登录
            responseNoLoginInfo(response);
            return false;
        }
        // 从 redis 中获取用户信息, 不存在则返回未登录
        String token = authorization.substring("Bearer ".length());
        String userInfoJsonStr = redisTemplate.opsForValue().get("user:login" + token);
        if (StrUtil.isEmpty(userInfoJsonStr)) {
            // 返回未登录
            responseNoLoginInfo(response);
            return false;
        }
        // 将用户信息转换为对象
        SysUser userInfo = JSON.parseObject(userInfoJsonStr, SysUser.class);
        // 用户信息存放至 ThreadLocal
        AuthContextUtil.set(userInfo);
        // 更新 redis 过期时间 30 分钟
        redisTemplate.expire("user:login" + token, 30, TimeUnit.MINUTES);
        // 放行
        return true;
    }

    // 返回 208 状态码，表示未登录
    private void responseNoLoginInfo(HttpServletResponse response) {
        // 封装返回结果
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        // 设置响应头信息
        response.setCharacterEncoding("UTF-8");
        // 设置响应类型
        response.setContentType("text/html; charset=utf-8");
        // 获取响应流
        try (PrintWriter writer = response.getWriter()) {
            // 输出响应信息
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            // 输出异常
            e.printStackTrace();
        }
    }
}
