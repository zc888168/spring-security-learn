package org.spring.security.learn.chapter4.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 验证码对象
 *
 * @author .
 */
@Data
public class ValidateCode {
    /**
     * 一个验证码包含三个信息
     */

    /**
     * code是一个随机数，图片根据这个随机数生成，这个随机数是要存入到session中的
     */
    private String code;
    /**
     * 验证码图片过期时间.
     */
    private LocalDateTime expireTime;

    /**
     * @param code
     * @param expireIn 多少秒过期
     */
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        //判断当前时间是否在过期时间之后
        return LocalDateTime.now().isAfter(expireTime);
    }

}
