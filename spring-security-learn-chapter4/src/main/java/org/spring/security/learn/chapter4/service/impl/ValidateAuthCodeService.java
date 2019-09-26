package org.spring.security.learn.chapter4.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.exception.ValidateCodeException;
import org.spring.security.learn.chapter4.properties.SecurityProperties;
import org.spring.security.learn.chapter4.consts.ValidateCodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * InitializingBean 实现此接口中的 afterPropertiesSet 初始化方法在其中初始化图片验证码拦截路径
 * 继承spring中的OncePerRequestFilter，确保每次请求调用一次过滤器
 *
 * @author .
 */

@Service
public class ValidateAuthCodeService {

    private static final Logger logger = LoggerFactory.getLogger(ValidateAuthCodeService.class);

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 验证请求url与配置的url是否匹配的工具类
     * 路径正则匹配工具
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();


    /**
     * 初始化要拦截的url配置信息
     */
    @PostConstruct
    public void init()  {

        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
        //TODO:还可以加图片的地址初始化

    }
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

        logger.info("*********ValidateCodeOncePerRequestFilter-doFilterInternal**********");

        ValidateCodeType type = getValidateCodeType(httpServletRequest);
        if (type != null) {
            logger.info("校验请求(" + httpServletRequest.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                        .validate(new ServletWebRequest(httpServletRequest, httpServletResponse));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                logger.error("验证码校验异常", exception);
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, exception);
                return;
            }
        }

       // filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    /**
     * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urlString
     * @param type
     */
    private void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }



    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())) {
            Set<String> neededVerifiedUrls = urlMap.keySet();
            for (String neededVerifiedUrl : neededVerifiedUrls) {
                if (antPathMatcher.match(neededVerifiedUrl, request.getRequestURI())) {
                    result = urlMap.get(neededVerifiedUrl);
                }
            }
        }
        return result;
    }
}
