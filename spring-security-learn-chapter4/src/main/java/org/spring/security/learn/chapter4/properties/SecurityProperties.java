/**
 *
 */
package org.spring.security.learn.chapter4.properties;

import lombok.Data;
import org.spring.security.learn.chapter4.entity.ValidateCodeVo;
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
    private ValidateCodeVo code = new ValidateCodeVo();

}