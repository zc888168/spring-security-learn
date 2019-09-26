package org.spring.security.learn.chapter4.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.consts.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * WebSecurityConfig
 *
 * @author .
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

//
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin().loginPage("/login").successForwardUrl("/user").permitAll()
                .and().logout().permitAll()
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("remember-me")
                .and()
                .apply(validateCodeSecurityConfig)
                .and()
               // .apply(smsCodeAuthenticationSecurityConfig)
                //.and()
                .authorizeRequests().antMatchers("/code/sms",
                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                logger.debug("你退出了登录");
                httpServletResponse.sendRedirect("/login");
            }
        };
    }
}
