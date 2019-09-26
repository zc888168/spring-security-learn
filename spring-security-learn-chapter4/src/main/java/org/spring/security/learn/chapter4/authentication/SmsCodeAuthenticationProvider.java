/**
 *
 */
package org.spring.security.learn.chapter4.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.security.MyUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * @author .
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(SmsCodeAuthenticationProvider.class);

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        logger.info("**********SmsCodeAuthenticationProvider-authenticate*********");

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        //token中的手机号
        String principal = (String) authenticationToken.getPrincipal();
        logger.info("principal:{}", principal);
        //根据手机号拿到对应的UserDetails
        UserDetails user = userDetailsService.loadUserByUsername(principal);

        if (user == null) {
            logger.error("数据库无法获取用户信息");
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
