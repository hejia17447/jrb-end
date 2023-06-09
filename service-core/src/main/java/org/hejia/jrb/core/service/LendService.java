package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.pojo.entity.Lend;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.BorrowInfoApprovalVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的准备表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface LendService extends IService<Lend> {

    /**
     * 创建项目标
     * @param borrowInfoApprovalVO 借款审批信息
     * @param borrowInfo 借款信息
     */
    void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo);

    /**
     * 获取标列表
     * @return 列表
     */
    List<Lend> selectList();

    /**
     * 根据标的id查询该标的信息
     * @param id 标id
     * @return 标信息
     */
    Map<String, Object> getLendDetail(Long id);

    /**
     * 计算投资收益
     * @param invest 投资金额
     * @param yearRate 年化收益
     * @param totalMonth 期数
     * @param returnMethod 还款方式
     * @return 收益
     */
    BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, Integer totalMonth, Integer returnMethod);

    /**
     * 满标放款
     * @param lendId 标id
     */
    void makeLoan(Long lendId);
}
