package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.common.exception.Assert;
import org.hejia.common.result.ResponseEnum;
import org.hejia.jrb.core.enums.BorrowInfoStatusEnum;
import org.hejia.jrb.core.enums.BorrowerStatusEnum;
import org.hejia.jrb.core.enums.UserBindEnum;
import org.hejia.jrb.core.mapper.BorrowInfoMapper;
import org.hejia.jrb.core.mapper.IntegralGradeMapper;
import org.hejia.jrb.core.mapper.UserInfoMapper;
import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.pojo.entity.IntegralGrade;
import org.hejia.jrb.core.pojo.entity.UserInfo;
import org.hejia.jrb.core.service.BorrowInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
@AllArgsConstructor
public class BorrowInfoServiceImpl extends ServiceImpl<BorrowInfoMapper, BorrowInfo> implements BorrowInfoService {

    private final UserInfoMapper userInfoMapper;

    private final IntegralGradeMapper integrationGradeMapper;

    /**
     * 根据用户id获取该用户的借款额度
     * @param userId 用户id
     * @return 借款额度
     */
    @Override
    public BigDecimal getBorrowAmount(Long userId) {

        // 获取用户积分
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
        Integer integral = userInfo.getIntegral();

        // 根据积分查询借款额度
        QueryWrapper<IntegralGrade> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("integral_start", integral);
        queryWrapper.ge("integral_end", integral);
        IntegralGrade integralGrade = integrationGradeMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(integral)) {
            return new BigDecimal("0");
        }

        return integralGrade.getBorrowAmount();
    }

    /**
     * 保存借款申请信息
     * @param borrowInfo 借款申请信息
     * @param userId 用户id
     */
    @Override
    public void saveBorrowInfo(BorrowInfo borrowInfo, Long userId) {
        // 获取用户树
        UserInfo userInfo = userInfoMapper.selectById(userId);

        // 判断用户绑定状态
        Assert.isTrue(userInfo.getBindStatus().intValue() == UserBindEnum.BIND_OK.getStatus().intValue(),
                ResponseEnum.USER_NO_BIND_ERROR);

        // 判断用户信息是否审批通过
        Assert.isTrue(
                userInfo.getBorrowAuthStatus().intValue() == BorrowerStatusEnum.AUTH_OK.getStatus().intValue(),
                ResponseEnum.USER_NO_AMOUNT_ERROR);

        // 判断借款额度是否足够
        BigDecimal borrowAmount = this.getBorrowAmount(userId);
        Assert.isTrue(
                borrowInfo.getAmount().doubleValue() <= borrowAmount.doubleValue(),
                ResponseEnum.USER_AMOUNT_LESS_ERROR);

        // 存储数据
        borrowInfo.setUserId(userId);
        // 百分比转成小数
        borrowInfo.setBorrowYearRate( borrowInfo.getBorrowYearRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        borrowInfo.setStatus(BorrowInfoStatusEnum.CHECK_RUN.getStatus());
        baseMapper.insert(borrowInfo);

    }

    /**
     * 根据用户id获取用户申请借款的状态
     * @param userId 用户id
     * @return 状态信息
     */
    @Override
    public Integer getStatusByUserId(Long userId) {
        QueryWrapper<BorrowInfo> borrowInfoQueryWrapper = new QueryWrapper<>();
        borrowInfoQueryWrapper.select("status").eq("user_id", userId);
        List<Object> status = baseMapper.selectObjs(borrowInfoQueryWrapper);
        if (status.isEmpty()) {
            // 借款人尚未提交信息
            return BorrowInfoStatusEnum.NO_AUTH.getStatus();
        }
        return (Integer) status.get(0);
    }
}
