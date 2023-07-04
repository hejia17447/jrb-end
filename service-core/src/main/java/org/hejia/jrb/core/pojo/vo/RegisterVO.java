package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

@Data
public class RegisterVO {

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 密码
     */
    private String password;

}
