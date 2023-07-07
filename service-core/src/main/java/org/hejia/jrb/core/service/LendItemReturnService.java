package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface LendItemReturnService extends IService<LendItemReturn> {

    /**
     * 根据标id查询该标的回款记录
     * @param lendId 标的id
     * @param userId 用户id
     * @return 回款记录
     */
    List<LendItemReturn> selectByLendId(Long lendId, Long userId);

    /**
     * 添加还款明细
     * @param lendReturnId 还款计划id
     * @return 添加结果
     */
    List<Map<String, Object>> addReturnDetail(Long lendReturnId);

    /**
     * 根据还款计划id获取对应的回款计划列表
     * @param lendReturnId 还款计划id
     * @return 回款计划列表
     */
    List<LendItemReturn> selectLendItemReturnList(Long lendReturnId);
}
