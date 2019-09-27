
/*
 * <p>文件名称: SendAuthCodeFrequencyService</p>
 * <p>项目描述: KOCA 金证云原生平台 V1.0.0</p>
 * <p>公司名称: 深圳市金证科技股份有限公司</p>
 * <p>版权所有: 版权所有(C)2019-2020</p>
 */

package org.spring.security.learn.chapter4.service.impl;

import org.spring.security.learn.chapter4.properties.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p>一句话功能简述.</p>
 * <p>功能详细描述</p>
 *
 * @author .
 * @version 1.0.0
 * @since 1.0.0, 2019/09/27
 */

public class SendAuthCodeFrequencyService {

    private RedisTemplate redisTemplate;

    private SecurityProperties securityProperties;

    public boolean checkFrequency(String requestedSessionId) {
        Object count = redisTemplate.boundValueOps(requestedSessionId).get();
        if (null == count) {
            redisTemplate.boundValueOps(requestedSessionId).set(1);
            redisTemplate.boundValueOps(requestedSessionId).expire(securityProperties.getSessionAuthCodeTimeInterval(), TimeUnit.MINUTES);
        } else {
            int num = (int) count;
            if (num >= securityProperties.getSessionAuthCodeLimitNum()) {
                return false;
            } else {
                ++num;
                redisTemplate.boundValueOps(requestedSessionId).set(num);
                redisTemplate.boundValueOps(requestedSessionId).expire(securityProperties.getSessionAuthCodeTimeInterval(), TimeUnit.MINUTES);
            }
        }
        return true;

    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
