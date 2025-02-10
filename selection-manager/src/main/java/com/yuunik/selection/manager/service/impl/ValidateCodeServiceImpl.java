package com.yuunik.selection.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import com.yuunik.selection.manager.service.ValidateCodeService;
import com.yuunik.selection.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 生成验证码
    @Override
    public ValidateCodeVo getValidateCode() {
        // 生成验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(102, 32, 4, 2);
        // 获取验证码数值
        String code = circleCaptcha.getCode();
        // 获取验证码图片
        String imageBase64 = circleCaptcha.getImageBase64();
        // 生成存入 redis 的key 值
        String key = UUID.randomUUID().toString().replace("-", "");
        // 存入 redis
        redisTemplate.opsForValue().set("user:validate" + key, code, 5, TimeUnit.MINUTES);
        // 生成响应数据
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        // 设置验证码key
        validateCodeVo.setCodeKey(key);
        // 验证验证码value
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
