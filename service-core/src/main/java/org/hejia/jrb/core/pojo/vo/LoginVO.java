package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

/**
 * 登录对象
 */
@Data
public class LoginVO {

    private Integer userType;

    private String mobile;

    private String password;

}
