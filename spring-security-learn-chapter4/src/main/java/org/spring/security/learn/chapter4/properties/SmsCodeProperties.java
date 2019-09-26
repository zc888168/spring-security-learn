/**
 *
 */
package org.spring.security.learn.chapter4.properties;


import lombok.Data;

/**
 * @author .
 */
@Data
public class SmsCodeProperties {

    private int length = 6;

    private int expireIn = 60;

    private String url;

}
