/**
 *
 */
package org.spring.security.learn.chapter4.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 读取以my.security开头的配置项
 * @author ..
 */
@ConfigurationProperties(prefix = "spring.security")
@Data
public class SecurityProperties {

    /**
     * 短信验证码配置
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

}