package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.BorrowInfoApprovalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询借款信息列表
     * @return 列表信息
     */
    List<BorrowInfo> selectList();

    /**
     * 根据借款id查询详情信息
     * @param id 借款id
     * @return 详情信息
     */
    Map<String, Object> getBorrowInfoDetail(long id);

    /**
     * 审批借款
     * @param borrowInfoApprovalVO 借款审批信息
     */
    void approval(BorrowInfoApprovalVO borrowInfoApprovalVO);

}
