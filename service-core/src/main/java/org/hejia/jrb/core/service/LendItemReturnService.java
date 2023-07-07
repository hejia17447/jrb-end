package org.hejia.jrb.core.service;

import org.hejia.jrb.core.pojo.entity.LendItemReturn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
