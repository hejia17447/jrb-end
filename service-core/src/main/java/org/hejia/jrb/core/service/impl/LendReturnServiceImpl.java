package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.hejia.jrb.core.pojo.entity.LendReturn;
import org.hejia.jrb.core.mapper.LendReturnMapper;
import org.hejia.jrb.core.service.LendReturnService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 还款记录表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
public class LendReturnServiceImpl extends ServiceImpl<LendReturnMapper, LendReturn> implements LendReturnService {

    /**
     * 根据标id查询标的还款计划
     * @param lendId 标id
     * @return 还款计划
     */
    @Override
    public List<LendReturn> selectByLendId(Long lendId) {

        QueryWrapper<LendReturn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lend_id", lendId);
        return baseMapper.selectList(queryWrapper);

    }
}
