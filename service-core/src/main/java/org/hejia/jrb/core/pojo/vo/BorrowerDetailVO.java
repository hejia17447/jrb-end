package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 借款人信息详情
 */
@Data
public class BorrowerDetailVO {

    private Long userId;

    private String name;

    private String idCard;

    private String mobile;

    private String sex;

    private Integer age;

    private String education;

    private String marry;

    private String industry;

    private String income;

    private String returnSource;

    private String contactsName;

    private String contactsMobile;

    private String contactsRelation;

    private String status;

    private LocalDateTime createTime;

    private List<BorrowerAttachVO> borrowerAttachVOList;

}
