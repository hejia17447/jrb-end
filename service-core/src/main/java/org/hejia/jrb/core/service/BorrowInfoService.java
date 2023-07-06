package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 借款信息表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface BorrowInfoService extends IService<BorrowInfo> {

    /**
     * 根据用户id获取该用户的借款额度
     * @param userId 用户id
     * @return 借款额度
     */
    BigDecimal getBorrowAmount(Long userId);

    /**
     * 保存借款申请信息
     * @param borrowInfo 借款申请信息
     * @param userId 用户id
     */
    void saveBorrowInfo(BorrowInfo borrowInfo, Long userId);

    /**
     * 根据用户id获取用户申请借款的状态
     * @param userId 用户id
     * @return 状态信息
     */
    Integer getStatusByUserId(Long userId);

}
