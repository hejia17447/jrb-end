package org.hejia.jrb.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 一次还本还息工具类
 */
public class Amount4Helper {

    /**
     * 还款金额 = 本金 + 本金*月利率*期限
     * @param amount 本金
     * @param yearRate 年利率
     * @param totalMonth 总期限
     * @return 还款金额
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(BigDecimal amount, BigDecimal yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<>();
        BigDecimal monthInterest = yearRate.divide(new BigDecimal("12"), 8, RoundingMode.HALF_UP);
        BigDecimal multiply = amount.multiply(monthInterest).multiply(new BigDecimal(totalMonth));
        map.put(1, multiply);
        return map;
    }

    /**
     * 还款本金
     * @param amount 本金
     * @param yearRate 年利率
     * @param totalMonth 总期限
     * @return 还款本金
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(BigDecimal amount, BigDecimal yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<>();
        map.put(1, amount);
        return map;
    }

    /**
     * 总利息
     * @param amount 本金
     * @param yearRate 年利率
     * @param totalMonth 总期限
     * @return 总利息
     */
    public static BigDecimal getInterestCount(BigDecimal amount, BigDecimal yearRate, int totalMonth) {
        BigDecimal monthInterest = yearRate.divide(new BigDecimal("12"), 8, RoundingMode.HALF_UP);
        return amount.multiply(monthInterest).multiply(new BigDecimal(totalMonth)).divide(new BigDecimal("1"), 8, RoundingMode.HALF_UP);
    }

}