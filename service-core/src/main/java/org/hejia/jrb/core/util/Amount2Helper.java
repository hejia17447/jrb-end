package org.hejia.jrb.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.hejia.jrb.core.util.Amount1Helper.getBigDecimal;


/**
 * 等额本金工具类
 * 校验网址：<a href="http://www.xjumc.com/">网址</a>
 * 等额本金是指一种贷款的还款方式，是在还款期内把贷款数总额等分，每月偿还同等数额的本金和剩余贷款在该月所产生的利息，这样由于每月的还款本金额固定，
 *  * 而利息越来越少，借款人起初还款压力较大，但是随时间的推移每月还款数也越来越少。
 */
public class Amount2Helper {

    /**
     * 每月本息
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还利息
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipalInterest(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<>();
        // 每月本金
        BigDecimal monthPri = invest.divide(new BigDecimal(totalMonth), 8, RoundingMode.DOWN);
        // 获取月利率
        double monthRate = yearRate.divide(new BigDecimal(12), 8, RoundingMode.DOWN).doubleValue();
        monthRate = new BigDecimal(monthRate).setScale(8, RoundingMode.DOWN).doubleValue();
        for (int i = 1; i <= totalMonth; i++) {
            double monthRes = monthPri.doubleValue() + (invest.doubleValue() - monthPri.doubleValue() * (i - 1)) * monthRate;
            monthRes = new BigDecimal(monthRes).setScale(2, RoundingMode.DOWN).doubleValue();
            map.put(i, new BigDecimal(monthRes));
        }
        return map;
    }

    /**
     * 每月还款利息
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还本金
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        Map<Integer, BigDecimal> inMap = new HashMap<>();
        BigDecimal principal = invest.divide(new BigDecimal(totalMonth), 8, RoundingMode.DOWN);
        Map<Integer, BigDecimal> map = getPerMonthPrincipalInterest(invest, yearRate, totalMonth);
        for (Map.Entry<Integer, BigDecimal> entry : map.entrySet()) {
            BigDecimal principalInterestBigDecimal = new BigDecimal(entry.getValue().toString());
            BigDecimal interestBigDecimal = principalInterestBigDecimal.subtract(principal);
            interestBigDecimal = interestBigDecimal.setScale(2, RoundingMode.DOWN);
            inMap.put(entry.getKey(), interestBigDecimal);
        }
        return inMap;
    }

    /**
     * 每月还款本金
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalMonth 还款总月数
     * @return 总利息
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<>();
        BigDecimal monthIncome = invest.divide(new BigDecimal(totalMonth), 8, RoundingMode.DOWN);
        for(int i=1; i<=totalMonth; i++) {
            map.put(i, monthIncome);
        }
        return map;
    }

    /**
     * 总利息
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalMonth 还款总月数
     * @return 总利息
     */
    public static BigDecimal getInterestCount(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        return getBigDecimal(getPerMonthInterest(invest, yearRate, totalMonth), invest, yearRate, totalMonth);
    }

}