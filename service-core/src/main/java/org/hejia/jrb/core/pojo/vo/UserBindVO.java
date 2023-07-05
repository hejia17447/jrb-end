package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

/**
 * 账户绑定VO
 */
@Data
public class UserBindVO {

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 银行类型
     */
    private String bankType;

    /**
     * 银行卡号
     */
    private String bankNo;

    /**
     * 手机号
     */
    private String mobile;

}
