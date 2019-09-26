package org.spring.security.learn.chapter4.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.service.SmsCodeSender;

/**
 * sms 实现.
 * @author  zc
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSmsCodeSender.class);

    @Override
    public void send(String mobile, String code) {

        //TODO:真实的项目中，这里要接入短信发送平台
        logger.info("向手机发送短信验证码 mobile : [{}] --- code : [{}]", mobile, code);


    }
}
