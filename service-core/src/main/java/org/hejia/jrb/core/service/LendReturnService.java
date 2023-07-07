package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.LendReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 还款记录表 服务类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
public interface LendReturnService extends IService<LendReturn> {

    /**
     * 根据标id查询标的还款计划
     * @param lendId 标id
     * @return 还款计划
     */
    List<LendReturn> selectByLendId(Long lendId);

    /**
     * 还款
     * @param lendReturnId 还款计划id
     * @param userId 用户id
     * @return 还款结果
     */
    String commitReturn(Long lendReturnId, Long userId);

    /**
     * 回到
     * @param paramMap 保存参数
     */
    void notify(Map<String, Object> paramMap);


}
