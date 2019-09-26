/**
 *
 */
package org.spring.security.learn.chapter4.properties;


import lombok.Data;

/**
 * @author .
 */

@Data
public class ValidateCodeProperties {

    private SmsCodeProperties sms = new SmsCodeProperties();

}
