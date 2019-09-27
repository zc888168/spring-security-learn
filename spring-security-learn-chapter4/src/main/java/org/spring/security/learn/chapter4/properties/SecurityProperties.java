/**
 *
 */
package org.spring.security.learn.chapter4.properties;

import lombok.Data;
import org.spring.security.learn.chapter4.entity.ValidateAuthCodeVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 读取以my.security开头的配置项
 * @author ..
 */
@Configuration
@ConfigurationProperties(prefix = "spring.security")
@Data
public class SecurityProperties {

    /**
     * 短信验证码配置
     */
    private ValidateAuthCodeVo code = new ValidateAuthCodeVo();

    /**
     * session auth code 时间间隔(有效期)
     */
    private int sessionAuthCodeTimeInterval;

    /**
     * session auth code 次数限制
     */
    private int sessionAuthCodeLimitNum;

}