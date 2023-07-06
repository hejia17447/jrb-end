package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.bo.TransFlowBO;
import org.hejia.jrb.core.pojo.entity.TransFlow;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
