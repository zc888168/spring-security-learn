/**
 *
 */
package org.spring.security.learn.chapter4.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.authentication.SmsCodeAuthenticationToken;
import org.spring.security.learn.chapter4.consts.SecurityConstants;
import org.spring.security.learn.chapter4.service.impl.ValidateAuthCodeService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zc.
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(SmsCodeAuthenticationFilter.class);

    private String mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;

    private ValidateAuthCodeService validateAuthCodeService;
    /**
     * 当前处理器是否处理post请求.
     */
    private boolean postOnly = true;


    // ~ Constructors
    // ===================================================================================================

    /**
     * 当前过滤器处理的请求是什么
     * 一个路径匹配器  手机表单登录的一个请求
     */
    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.NEED_SMS_CODE_AUTHENTICATION_URL, HttpMethod.POST.name()));

    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        logger.info("*********SmsCodeAuthenticationFilter--attemptAuthentication********");
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            //当前请求如果不是post请求则抛出异常
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //
        String mobile = obtainMobile(request);
        logger.debug("SmsCodeAuthenticationFilter 从请求中获取mobile:[{}]参数", mobile);

        mobile = null == mobile ? "" : mobile.trim();

        logger.debug("实例化token mobile: [{}]", mobile);
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        logger.debug("使用AuthenticationManager 调用 token ");
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 获取手机号
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the
     * authentication request's details property.
     *
     * @param request
     *            that an authentication request is being created for
     * @param authRequest
     *            the authentication request object that should have its details
     *            set
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from
     * the login request.
     *
     * @param usernameParameter
     *            the parameter name. Defaults to "username".
     */
    public void setMobileParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.mobileParameter = usernameParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter.
     * If set to true, and an authentication request is received which is not a
     * POST request, an exception will be raised immediately and authentication
     * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
     * will be called as if handling a failed authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

    public ValidateAuthCodeService getValidateAuthCodeService() {
        return validateAuthCodeService;
    }

    public void setValidateAuthCodeService(ValidateAuthCodeService validateAuthCodeService) {
        this.validateAuthCodeService = validateAuthCodeService;
    }
}
