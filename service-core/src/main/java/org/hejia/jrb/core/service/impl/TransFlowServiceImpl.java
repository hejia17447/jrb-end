package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.jrb.core.mapper.TransFlowMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.pojo.bo.TransFlowBO;
import org.hejia.jrb.core.pojo.entity.TransFlow;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.service.TransFlowService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 交易流水表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
@AllArgsConstructor
public class TransFlowServiceImpl extends ServiceImpl<TransFlowMapper, TransFlow> implements TransFlowService {

    private final UserInfoMapper userInfoMapper;

    /**
     * 保存交易流水
     * @param transFlowBO 流水信息
     */
    @Override
    public void saveTransFlow(TransFlowBO transFlowBO) {
        // 获取用户基本信息 user_info
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("bind_code", transFlowBO.getBindCode());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);

        // 存储交易流水数据
        TransFlow transFlow = new TransFlow();
        transFlow.setUserId(userInfo.getId());
        transFlow.setUserName(userInfo.getName());
        transFlow.setTransNo(transFlowBO.getAgentBillNo());
        transFlow.setTransType(transFlowBO.getTransTypeEnum().getTransType());
        transFlow.setTransTypeName(transFlowBO.getTransTypeEnum().getTransTypeName());
        transFlow.setTransAmount(transFlowBO.getAmount());
        transFlow.setMemo(transFlowBO.getMemo());
        baseMapper.insert(transFlow);

    }

    /**
     * 判断流水是否存在
     * @param agentBillNo 流水号
     * @return 是否有该流水
     */
    @Override
    public boolean isSaveTransFlow(String agentBillNo) {
        QueryWrapper<TransFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trans_no", agentBillNo);
        Long count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    /**
     * 根据用户id获取交易流水
     * @param userId 用户id
     * @return 交易流水
     */
    @Override
    public List<TransFlow> selectByUserId(Long userId) {

        QueryWrapper<TransFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("user_id", userId)
                .orderByDesc("id");
        return baseMapper.selectList(queryWrapper);

    }
}
