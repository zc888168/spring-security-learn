package org.spring.security.learn.chapter1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${spring.csrf.enabled}")
	private boolean enabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/anon1", "/anon2").permitAll().anyRequest().authenticated().and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/user").permitAll().and().logout().permitAll();
		if(!enabled) {
			http.csrf().disable();
		}
//		RequestMatcher requestMatcher = new RequestMatcher(){
//
//			/**
//			 * http://127.0.0.1:9090/user?token=abc 可以访问
//			 * http://127.0.0.1:9090/user  不能够访问
//			 * @param request
//			 * @return
//			 */
//			@Override
//			public boolean matches(HttpServletRequest request) {
//				if(!request.getRequestURI().contains("user1")){
//					return false;
//				}
//				/**
//				 * 拦截命令行伪造的请求
//				 */
//				if(StringUtils.isEmpty(request.getParameter("token"))){
//					return true;
//				}
//				if(request.getParameter("token").contains("/login")){
//					System.out.println("test success");
//					return false;
//				}
//				return false;
//			}
//		};
//		http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository())
//				.requireCsrfProtectionMatcher(requestMatcher);

	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user")
				.password(new BCryptPasswordEncoder().encode("111111")).roles("USER");
	}
}
