package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 借款信息审批
 */
@Data
public class BorrowInfoApprovalVO {

    /**
     * id
     */
    private Long id;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 审批内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 年化利率
     */
    private BigDecimal lendYearRate;

    /**
     * 平台服务率
     */
    private BigDecimal serviceRate;

    /**
     * 开始日期
     */
    private String lendStartDate;

    /**
     * 描述信息
     */
    private String lendInfo;

}
