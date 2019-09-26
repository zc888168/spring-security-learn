/**
 *
 */
package org.spring.security.learn.chapter4.entity;


import lombok.Data;

/**
 * @author .
 */
@Data
public class SmsAuthCodeVo {

    /**
     * 验证码长度.
     */
    private int length = 6;

    /**
     * 验证码有效期，单位为秒.
     */
    private int expireIn = 60;

    /**
     * 需要验证码二次验证的url.
     */
    private String url;

}
