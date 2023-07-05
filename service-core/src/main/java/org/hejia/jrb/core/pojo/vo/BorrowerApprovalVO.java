package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

/**
 * 借款人审批
 */
@Data
public class BorrowerApprovalVO {

    private Long borrowerId;

    private Integer status;

    private Boolean isIdCardOk;

    private Boolean isHouseOk;

    private Boolean isCarOk;

    private Integer infoIntegral;

}
