package org.hejia.jrb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.hejia.jrb.core.enums.LendStatusEnum;
import org.hejia.jrb.core.mapper.LendMapper;
import org.hejia.jrb.core.pojo.entity.BorrowInfo;
import org.hejia.jrb.core.pojo.entity.Lend;
import org.hejia.jrb.core.pojo.vo.BorrowInfoApprovalVO;
import org.hejia.jrb.core.service.DictService;
import org.hejia.jrb.core.service.LendService;
import org.hejia.jrb.core.util.LendNoUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author HJ
 * @since 2023-05-31
 */
@Service
@AllArgsConstructor
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {

    private final DictService dictService;

    /**
     * 创建项目标
     * @param borrowInfoApprovalVO 借款审批信息
     * @param borrowInfo 借款信息
     */
    @Override
    public void createLend(BorrowInfoApprovalVO borrowInfoApprovalVO, BorrowInfo borrowInfo) {
        Lend lend = new Lend();
        lend.setUserId(borrowInfo.getUserId());
        lend.setBorrowInfoId(borrowInfo.getUserId());
        lend.setLendNo(LendNoUtils.getLendNo());//生成编号
        lend.setTitle(borrowInfoApprovalVO.getTitle());
        lend.setAmount(borrowInfo.getAmount());
        lend.setPeriod(borrowInfo.getPeriod());
        // 从审批对象中获取
        lend.setLendYearRate(borrowInfoApprovalVO.getLendYearRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        //从审批对象中获取
        lend.setServiceRate(borrowInfoApprovalVO.getServiceRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        lend.setReturnMethod(borrowInfo.getReturnMethod());
        lend.setLowestAmount(new BigDecimal(100));
        lend.setInvestAmount(new BigDecimal(0));
        lend.setInvestNum(0);
        lend.setPublishDate(LocalDateTime.now());

        // 起息日期
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lendStartDate = LocalDate.parse(borrowInfoApprovalVO.getLendStartDate(), dtf);
        lend.setLendStartDate(lendStartDate);

        // 结束日期
        LocalDate lendEndDate = lendStartDate.plusMonths(borrowInfo.getPeriod());
        lend.setLendEndDate(lendEndDate);

        // 描述
        lend.setLendInfo(borrowInfoApprovalVO.getLendInfo());

        //平台预期收益
        //        月年化 = 年化 / 12
        BigDecimal monthRate = lend.getServiceRate().divide(new BigDecimal(12), 8, RoundingMode.DOWN);
        //        平台收益 = 标的金额 * 月年化 * 期数
        BigDecimal expectAmount = lend.getAmount().multiply(monthRate).multiply(new BigDecimal(lend.getPeriod()));
        lend.setExpectAmount(expectAmount);

        //实际收益
        lend.setRealAmount(new BigDecimal(0));
        //状态
        lend.setStatus(LendStatusEnum.INVEST_RUN.getStatus());
        //审核时间
        lend.setCheckTime(LocalDateTime.now());
        //审核人
        lend.setCheckAdminId(1L);

        baseMapper.insert(lend);


    }

    /**
     * 获取标列表
     * @return 列表
     */
    @Override
    public List<Lend> selectList() {

        List<Lend> lends = baseMapper.selectList(null);
        lends.forEach(lend -> {
            String returnMethod = dictService.getNameByParentDictCodeAndValue("returnMethod", lend.getReturnMethod());
            String status = LendStatusEnum.getMsgByStatus(lend.getStatus());
            lend.getParam().put("returnMethod", returnMethod);
            lend.getParam().put("status", status);
        });

        return lends;
    }
}
