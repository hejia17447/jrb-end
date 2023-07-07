package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.LendItem;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hejia.jrb.core.pojo.vo.InvestVO;

import java.util.List;
import java.util.Map;

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


    /**
     * 回调方法，添加交易流水
     * @param paramMap 回调信息
     */
    void notify(Map<String, Object> paramMap);

    /**
     * 根据lendId获取投资记录
     * @param lendId 投资id
     * @param status 投资状态
     * @return 投资信息
     */
    List<LendItem> selectByLendId(Long lendId, int status);
}
