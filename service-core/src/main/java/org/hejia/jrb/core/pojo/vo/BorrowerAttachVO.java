package org.hejia.jrb.core.pojo.vo;

import lombok.Data;

/**
 * 借款人附件资料
 */
@Data
public class BorrowerAttachVO {

    /**
     * 图片类型（idCard1：身份证正面，idCard2：身份证反面，house：房产证，car：车）
     */
    private String imageType;

    private String imageUrl;

}
