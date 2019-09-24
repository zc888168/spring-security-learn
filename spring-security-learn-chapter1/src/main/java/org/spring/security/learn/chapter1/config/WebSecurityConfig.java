package org.spring.security.learn.chapter1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

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
		} else {
			CookieCsrfTokenRepository cookieCsrfTokenRepository =   new CookieCsrfTokenRepository();
			cookieCsrfTokenRepository.setCookieHttpOnly(false);
			http.csrf().csrfTokenRepository(cookieCsrfTokenRepository);
		}
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("user")
				.password(new BCryptPasswordEncoder().encode("111111")).roles("USER");
	}
}
