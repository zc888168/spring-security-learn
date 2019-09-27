/**
 * 
 */
package org.spring.security.learn.chapter4.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.security.learn.chapter4.exception.ValidateCodeException;
import org.spring.security.learn.chapter4.consts.ValidateCodeType;
import org.spring.security.learn.chapter4.repository.ValidateCodeRepository;
import org.spring.security.learn.chapter4.service.ValidateCodeGenerator;
import org.spring.security.learn.chapter4.service.ValidateCodeProcessor;
import org.spring.security.learn.chapter4.entity.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * @author .
 * @param <C>
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	private static final Logger logger = LoggerFactory.getLogger(AbstractValidateCodeProcessor.class);

	@Autowired
	private ValidateCodeRepository validateCodeRepository;

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 *
	 * 这个map的注入
	 * spring启动的时候，会查找map的value接口ValidateCodeGenerator的所有实现bean，
	 * 并把这个bean为value，bean的名称为key存入map中
	 *
	 * 这种行为叫依赖搜索
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	/**
	 *
	 * @param request
	 * @throws Exception
	 */
	@Override
	public void create(ServletWebRequest request) throws Exception {
		logger.debug("**********AbstractValidateCodeProcessor-create**************");
		//生成
		C validateCode = generate(request);
		//保存
		save(request, validateCode);
		//发送 这是一个抽象方法 需要子类去实现
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + "CodeGenerator";
        logger.debug("AbstractValidateCodeProcessor-generatorName:{}",generatorName);

        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码，可以保存到数据库或者缓存
	 *
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		logger.debug("AbstractValidateCodeProcessor-save");
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType(request));

    }


	/**
	 * 发送校验码，由子类实现
     * 它的抽象方法 send由具体的子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

    	logger.debug("***********AbstractValidateCodeProcessor--handler**********");

		ValidateCodeType codeType = getValidateCodeType(request);
		logger.info("codeType:{}", codeType.getParamNameOnValidate());

		C codeInSession = (C) validateCodeRepository.get(request, codeType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					codeType.getParamNameOnValidate());

			logger.info("codeInRequest:{}", codeInRequest);

		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}

		if (codeInSession.isExpried()) {
			validateCodeRepository.remove(request, codeType);
			throw new ValidateCodeException("验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}

		validateCodeRepository.remove(request, codeType);
    }
}
