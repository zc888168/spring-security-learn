package org.spring.security.learn.chapter4.validate.sms;

/**
 * 短信验证码发送接口
 */
public interface SmsCodeSender {

    /**
     *  send.
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);

}
