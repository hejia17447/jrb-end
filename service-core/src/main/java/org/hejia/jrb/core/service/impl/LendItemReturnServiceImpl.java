package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.jrb.core.mapper.LendItemMapper;
import org.hejia.jrb.core.mapper.LendItemReturnMapper;
import org.hejia.jrb.core.mapper.LendMapper;
import org.hejia.jrb.core.mapper.LendReturnMapper;
import org.hejia.jrb.core.pojo.entity.Lend;
import org.hejia.jrb.core.pojo.entity.LendItem;
import org.hejia.jrb.core.pojo.entity.LendItemReturn;
import org.hejia.jrb.core.pojo.entity.LendReturn;
import org.hejia.jrb.core.service.LendItemReturnService;
import org.hejia.jrb.core.service.UserBindService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标的出借回款记录表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
@AllArgsConstructor
public class LendItemReturnServiceImpl extends ServiceImpl<LendItemReturnMapper, LendItemReturn> implements LendItemReturnService {

    private final UserBindService userBindService;

    private final LendItemMapper lendItemMapper;

    private final LendMapper lendMapper;

    private final LendReturnMapper lendReturnMapper;

    /**
     * 根据标id查询该标的回款记录
     * @param lendId 标的id
     * @param userId 用户id
     * @return 回款记录
     */
    @Override
    public List<LendItemReturn> selectByLendId(Long lendId, Long userId) {
        QueryWrapper<LendItemReturn> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("lend_id", lendId)
                .eq("invest_user_id", userId)
                .orderByAsc("current_period");
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 添加还款明细
     * @param lendReturnId 还款计划id
     * @return 添加结果
     */
    @Override
    public List<Map<String, Object>> addReturnDetail(Long lendReturnId) {

        // 获取还款记录
        LendReturn lendReturn = lendReturnMapper.selectById(lendReturnId);

        // 获取标的信息
        Lend lend = lendMapper.selectById(lendReturn.getLendId());

        // 根据还款id获取回款列表
        List<LendItemReturn> lendItemReturnList = this.selectLendItemReturnList(lendReturnId);
        List<Map<String, Object>> lendItemReturnDetailList = new ArrayList<>();
        for(LendItemReturn lendItemReturn : lendItemReturnList) {
            LendItem lendItem = lendItemMapper.selectById(lendItemReturn.getLendItemId());
            String bindCode = userBindService.getBindCodeByUserId(lendItem.getInvestUserId());

            Map<String, Object> map = new HashMap<>();
            // 项目编号
            map.put("agentProjectCode", lend.getLendNo());
            // 出借编号
            map.put("voteBillNo", lendItem.getLendItemNo());
            // 收款人（出借人）
            map.put("toBindCode", bindCode);
            // 还款金额
            map.put("transitAmt", lendItemReturn.getTotal());
            // 还款本金
            map.put("baseAmt", lendItemReturn.getPrincipal());
            // 还款利息
            map.put("benifitAmt", lendItemReturn.getInterest());
            // 商户手续费
            map.put("feeAmt", new BigDecimal("0"));

            lendItemReturnDetailList.add(map);
        }
        return lendItemReturnDetailList;

    }

    @Override
    public List<LendItemReturn> selectLendItemReturnList(Long lendReturnId) {
        QueryWrapper<LendItemReturn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lend_return_id", lendReturnId);
        return baseMapper.selectList(queryWrapper);
    }
}
