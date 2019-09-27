/**
 * 
 */
package org.spring.security.learn.chapter4.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.service.SmsCodeSender;
import org.spring.security.learn.chapter4.entity.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码处理器
 * @author .
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	private static final Logger logger = LoggerFactory.getLogger(SmsCodeProcessor.class);

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String mobile= ServletRequestUtils.getRequiredStringParameter(request.getRequest(),"mobile");
		logger.debug("mobile:{},code:{}", mobile, validateCode.getCode());
		smsCodeSender.send(mobile, validateCode.getCode());
	}

}
