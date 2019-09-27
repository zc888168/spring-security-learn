package org.spring.security.learn.chapter4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.consts.SecurityConstants;
import org.spring.security.learn.chapter4.properties.SecurityProperties;
import org.spring.security.learn.chapter4.service.ValidateCodeProcessor;
import org.spring.security.learn.chapter4.service.impl.SendAuthCodeFrequencyService;
import org.spring.security.learn.chapter4.service.impl.ValidateCodeProcessorHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;


/**
 * @author .
 */
@RestController
public class ValidateCodeController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);


    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    @Resource
    private SendAuthCodeFrequencyService sendAuthCodeFrequencyService;

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public String createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        logger.info("验证短信或邮件每个会话在minute:[{}]分钟内只能发送sendCount:[{}]条，并且必须在服务端做限制",
                securityProperties.getSessionAuthCodeTimeInterval(), securityProperties.getSessionAuthCodeLimitNum());
        boolean approval = sendAuthCodeFrequencyService.checkFrequency(request.getRequestedSessionId());
        if(!approval){
            logger.info("frequencyControl Send frequently, please try again later");
            return "failure";
        }
        logger.debug("***********createCode**********");
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
        logger.info("Send verification code Successful");
        return "success";
    }

}
