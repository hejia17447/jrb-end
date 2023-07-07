package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.LendReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
