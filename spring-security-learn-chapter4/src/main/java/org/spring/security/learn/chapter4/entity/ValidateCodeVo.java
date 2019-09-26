/**
 *
 */
package org.spring.security.learn.chapter4.entity;


import lombok.Data;
import org.spring.security.learn.chapter4.entity.SmsAuthCodeVo;

/**
 * @author .
 */

@Data
public class ValidateCodeVo {

    private SmsAuthCodeVo sms = new SmsAuthCodeVo();

}
