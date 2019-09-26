/**
 * 
 */
package org.spring.security.learn.chapter4.service;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * @author .
 *
 */
public interface ValidateCodeProcessor {

	/**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

	/**
	 * 创建校验码
	 * 
	 * @param request
	 * @throws Exception
	 * ServletWebRequest spring提供的一个工具类  可以封装请求和响应
	 */
	void create(ServletWebRequest request) throws Exception;


	/**
	 * 校验验证码
	 *
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}
