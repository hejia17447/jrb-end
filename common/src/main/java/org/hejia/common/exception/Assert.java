package org.hejia.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.ResponseEnum;
import org.springframework.util.StringUtils;

@Slf4j
public abstract class Assert {

    /**
     * 断言对象不为空
     * 如果对象obj为空，则抛出异常
     * @param obj 待判断对象
     * @param responseEnum 可能出现的异常
     */
    public static void notNull(Object obj, ResponseEnum responseEnum) {
        if (obj == null) {
            log.info("obj是空的。。。。。");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言对象为空
     * 如果对象obj不为空，则抛出异常
     * @param object 判断对象
     * @param responseEnum 可能出现异常
     */
    public static void isNull(Object object, ResponseEnum responseEnum) {
        if (object != null) {
            log.info("obj是空的。。。。。");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言表达式为真
     * 如果不为真，则抛出异常
     * @param responseEnum 可能出现异常
     * @param expression 是否成功
     */
    public static void isTrue(boolean expression, ResponseEnum responseEnum) {
        if (!expression) {
            log.info("失败。。。。");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象不相等
     * 如果相等，则抛出异常
     * @param m1 对象1
     * @param m2 对象2
     * @param responseEnum 可能出现异常
     */
    public static void notEquals(Object m1, Object m2,  ResponseEnum responseEnum) {
        if (m1.equals(m2)) {
            log.info("相等。。。。。");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言两个对象相等
     * 如果不相等，则抛出异常
     * @param m1 对象1
     * @param m2 对象2
     * @param responseEnum 可能出现异常
     */
    public static void equals(Object m1, Object m2,  ResponseEnum responseEnum) {
        if (!m1.equals(m2)) {
            log.info("不相等。。。。。");
            throw new BusinessException(responseEnum);
        }
    }

    /**
     * 断言参数不为空
     * 如果为空，则抛出异常
     * @param s 参数
     * @param responseEnum 异常
     */
    public static void notEmpty(String s, ResponseEnum responseEnum) {
        if (!StringUtils.hasLength(s)) {
            log.info("为空。。。。");
            throw new BusinessException(responseEnum);
        }
    }

}
