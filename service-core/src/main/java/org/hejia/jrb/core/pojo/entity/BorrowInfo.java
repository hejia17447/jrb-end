package org.hejia.jrb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 借款信息表
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BorrowInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 借款用户id
     */
    private Long userId;

    /**
     * 借款金额
     */
    private BigDecimal amount;

    /**
     * 借款期限
     */
    private Integer period;

    /**
     * 年化利率
     */
    private BigDecimal borrowYearRate;

    /**
     * 还款方式 1-等额本息 2-等额本金 3-每月还息一次还本 4-一次还本
     */
    private Integer returnMethod;

    /**
     * 资金用途
     */
    private Integer moneyUse;

    /**
     * 状态（0：未提交，1：审核中， 2：审核通过， -1：审核不通过）
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

    //扩展字段
    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String mobile;

    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();


}
