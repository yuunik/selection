package com.yuunik.selection.manager.service.impl;

import com.alibaba.fastjson2.JSON;
import com.yuunik.selection.common.constant.ResultCode;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.mapper.SysUserMapper;
import com.yuunik.selection.manager.service.SysUserService;
import com.yuunik.selection.model.dto.system.LoginDto;
import com.yuunik.selection.model.entity.system.SysUser;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import com.yuunik.selection.model.vo.system.LoginVo;
import com.yuunik.selection.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     * @param loginDto 登录成功响应结果实体类
     * @return
     */
    @Override
    public LoginVo login(LoginDto loginDto) {
        // 获取用户信息
        String userName = loginDto.getUserName();
        // 查询用户是否存在
        SysUser user = sysUserMapper.selectUserInfoByUserName(userName);
        if (user == null) {
            throw new YuunikException(ResultCodeEnum.USER_NOT_EXISTS);
        }
        // 校验密码
        String password = user.getPassword();
        if (!DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes()).equals(password)) {
            throw new YuunikException(ResultCodeEnum.LOGIN_ERROR);
        }
        // 生成 token 信息
        String token = "Bear " + JwtUtil.getJwtToken(user.getId(), user.getUserName());
        // 存放 token
        redisTemplate.opsForValue().set("user:login", JSON.toJSONString(token), 7, TimeUnit.DAYS);
        // 封装响应信息
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }
}
