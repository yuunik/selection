package com.yuunik.selection.util;

import com.yuunik.selection.model.entity.system.SysUser;

/**
 * @author yuunik
 * 获取当前登录用户信息
 */
public class AuthContextUtil {
    // 线程变量隔离
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    // set
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // get
    public static SysUser get() {
        return threadLocal.get();
    }

    // delete
    public static void remove() {
        threadLocal.remove();
    }
}
