/**
 * 
 */
package org.spring.security.learn.chapter4.service.impl;


import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.properties.SecurityProperties;
import org.spring.security.learn.chapter4.entity.ValidateCode;
import org.spring.security.learn.chapter4.service.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器实现
 * @author .
 */
@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SmsCodeGenerator.class);


	@Autowired
	private SecurityProperties securityProperties;
	

	@Override
	public ValidateCode generate(ServletWebRequest request) {

		logger.info("************SmsCodeGenerator-generate*************");
		//这里采用随机数生成短信验证码
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}
	

}
