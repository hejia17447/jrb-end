package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.pojo.entity.Lend;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.BorrowInfoApprovalVO;

import java.util.List;

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

}
