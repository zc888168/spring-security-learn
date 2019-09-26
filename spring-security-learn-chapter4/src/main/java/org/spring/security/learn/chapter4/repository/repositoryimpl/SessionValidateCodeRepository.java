package org.spring.security.learn.chapter4.repository.repositoryimpl;


import org.spring.security.learn.chapter4.constants.ValidateCodeType;
import org.spring.security.learn.chapter4.repository.ValidateCodeRepository;
import org.spring.security.learn.chapter4.entity.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author .
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";


    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        request.getRequest().getSession().setAttribute(getSessionKey(request, validateCodeType), code);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) request.getRequest().getSession().getAttribute(getSessionKey(request, validateCodeType));
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType codeType) {
        request.getRequest().getSession().removeAttribute(getSessionKey(request, codeType));
    }

    /**
     * 构建验证码翻入session得可以
     *
     * @return
     */
    private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return SESSION_KEY_PREFIX + validateCodeType.toString().toLowerCase();
    }
}
