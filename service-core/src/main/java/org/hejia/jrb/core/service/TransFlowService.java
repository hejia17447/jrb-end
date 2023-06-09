package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.bo.TransFlowBO;
import org.hejia.jrb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface TransFlowService extends IService<TransFlow> {

    /**
     * 保存交易流水
     * @param transFlowBO 流水信息
     */
    void saveTransFlow(TransFlowBO transFlowBO);

    /**
     * 判断流水是否存在
     * @param agentBillNo 流水号
     * @return 是否有该流水
     */
    boolean isSaveTransFlow(String agentBillNo);

    /**
     * 根据用户id获取交易流水
     * @param userId 用户id
     * @return 交易流水
     */
    List<TransFlow> selectByUserId(Long userId);
}
