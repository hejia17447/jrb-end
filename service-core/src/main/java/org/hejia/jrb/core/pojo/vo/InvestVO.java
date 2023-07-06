package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

/**
 * 投标信息
 */
@Data
public class InvestVO {

    private Long lendId;

    // 投标金额
    private String investAmount;

    // 用户id
    private Long investUserId;

    // 用户姓名
    private String investName;

}
