package org.hejia.jrb.core.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 标的出借记录表
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LendItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 投资编号
     */
    private String lendItemNo;

    /**
     * 标的id
     */
    private Long lendId;

    /**
     * 投资用户id
     */
    private Long investUserId;

    /**
     * 投资人名称
     */
    private String investName;

    /**
     * 投资金额
     */
    private BigDecimal investAmount;

    /**
     * 年化利率
     */
    private BigDecimal lendYearRate;

    /**
     * 投资时间
     */
    private LocalDateTime investTime;

    /**
     * 开始日期
     */
    private LocalDate lendStartDate;

    /**
     * 结束日期
     */
    private LocalDate lendEndDate;

    /**
     * 预期收益
     */
    private BigDecimal expectAmount;

    /**
     * 实际收益
     */
    private BigDecimal realAmount;

    /**
     * 状态（0：默认 1：已支付 2：已还款）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除(1:已删除，0:未删除)
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
