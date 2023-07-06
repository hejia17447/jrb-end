package org.hejia.jrb.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本息工具类
 * 校验网址：<a href="http://www.xjumc.com/">网址</a>
 * 等额本息是指一种贷款的还款方式，是在还款期内，每月偿还同等数额的贷款(包括本金和利息)，和等额本金是不一样的概念，虽然刚开始还款时每月还款额可能会低于等额本金还款方式，但是最终所还利息会高于等额本金还款方式，该方式经常被银行使用。
 * 每月还款数额计算公式如下：
 * 每月还款额=贷款本金×[月利率×(1+月利率) ^ 还款月数]÷{[(1+月利率) ^ 还款月数]-1}
 */
public class Amount1Helper {

    /**
     * 每月还款利息
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还利息
     */
    public static Map<Integer, BigDecimal> getPerMonthInterest(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        Map<Integer, BigDecimal> map = new HashMap<>();
        //月利息
        double monthRate = yearRate.divide(new BigDecimal("12"), 8, RoundingMode.DOWN).doubleValue();
        BigDecimal monthInterest;
        for (int i = 1; i < totalMonth + 1; i++) {
            BigDecimal multiply = invest.multiply(new BigDecimal(monthRate));
            BigDecimal sub  = BigDecimal.valueOf(Math.pow(1 + monthRate, totalMonth)).subtract(BigDecimal.valueOf(Math.pow(1 + monthRate, i - 1)));
            monthInterest = multiply.multiply(sub).divide(BigDecimal.valueOf(Math.pow(1 + monthRate, totalMonth) - 1), 8, RoundingMode.DOWN);
            monthInterest = monthInterest.setScale(2, RoundingMode.DOWN);
            map.put(i, monthInterest);
        }
        return map;
    }

    /**
     * 每月还款本金
     * @param invest 总借款额（贷款本金）
     * @param yearRate 年利率
     * @param totalMonth 还款总月数
     * @return 每月偿还本金
     */
    public static Map<Integer, BigDecimal> getPerMonthPrincipal(BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        double monthRate = yearRate.divide(new BigDecimal("12"), 8, RoundingMode.DOWN).doubleValue();
        BigDecimal monthIncome = invest.multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalMonth)))
                .divide(BigDecimal.valueOf(Math.pow(1 + monthRate, totalMonth) - 1), 8, RoundingMode.DOWN);
        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, totalMonth);
        Map<Integer, BigDecimal> mapPrincipal = new HashMap<>();

        for (Map.Entry<Integer, BigDecimal> entry : mapInterest.entrySet()) {
            mapPrincipal.put(entry.getKey(), monthIncome.subtract(entry.getValue()));
        }
        return mapPrincipal;
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

    static BigDecimal getBigDecimal(Map<Integer, BigDecimal> perMonthInterest, BigDecimal invest, BigDecimal yearRate, int totalMonth) {
        BigDecimal count = new BigDecimal(0);

        for (Map.Entry<Integer, BigDecimal> entry : perMonthInterest.entrySet()) {
            count = count.add(entry.getValue());
        }
        return count;
    }

}
