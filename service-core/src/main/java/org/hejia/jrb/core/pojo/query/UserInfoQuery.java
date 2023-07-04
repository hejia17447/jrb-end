package org.hejia.jrb.core.pojo.query;

import lombok.Data;

/**
 * 会员搜索对象
 */
@Data
public class UserInfoQuery {

    private String mobile;

    private Integer status;

    /**
     * 1：出借人 2：借款人
     */
    private Integer userType;

}
