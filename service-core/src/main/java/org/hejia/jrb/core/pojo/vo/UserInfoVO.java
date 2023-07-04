package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

/**
 * 用户信息对象
 */
@Data
public class UserInfoVO {

    private String name;

    private String nickName;

    private String headImg;

    private String mobile;

    private Integer userType;

    private String token;

}
