package org.spring.security.learn.chapter4.repository;

import org.spring.security.learn.chapter4.consts.ValidateCodeType;
import org.spring.security.learn.chapter4.entity.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 * 我们可以根据需要实现不同类型的存取器，比如基于session的，或者直接使用数据库
 * @author .
 */
public interface ValidateCodeRepository {
    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);

}
