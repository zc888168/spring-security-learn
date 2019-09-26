/**
 * 
 */
package org.spring.security.learn.chapter4.config;

import org.spring.security.learn.chapter4.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 使SecurityProperties中的配置生效
 * @author .
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
