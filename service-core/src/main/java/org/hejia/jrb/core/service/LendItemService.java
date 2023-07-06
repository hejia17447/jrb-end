package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.LendItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.InvestVO;

/**
 * <p>
 * 标的出借记录表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface LendItemService extends IService<LendItem> {

    /**
     * 提交投资
     * @param investVO 投资信息
     * @return 投资结果
     */
    String commitInvest(InvestVO investVO);


}
