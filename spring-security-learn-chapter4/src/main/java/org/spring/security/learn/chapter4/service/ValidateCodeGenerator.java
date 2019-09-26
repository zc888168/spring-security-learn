package org.spring.security.learn.chapter4.service;

import org.spring.security.learn.chapter4.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 * @author .
 */
public interface ValidateCodeGenerator {
    /**
     * 生成验证码
     */
    ValidateCode generate(ServletWebRequest request);
}
