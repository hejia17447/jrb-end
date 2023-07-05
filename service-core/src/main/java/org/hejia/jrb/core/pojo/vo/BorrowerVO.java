package org.hejia.jrb.core.pojo.vo;

import lombok.Data;
import org.hejia.jrb.core.pojo.entity.BorrowerAttach;

import java.util.List;

/**
 * 借款人认证信息
 */
@Data
public class BorrowerVO {

    private Integer sex;

    private Integer age;

    private Integer education;

    private Boolean marry;

    private Integer industry;

    private Integer income;

    private Integer returnSource;

    private String contactsName;

    private String contactsMobile;

    private Integer contactsRelation;

    private List<BorrowerAttach> borrowerAttachList;

}
