package org.hejia.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hejia.common.result.ResponseEnum;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException{

    // 状态码
    private Integer code;

    // 错误信息
    private String message;

    /**
     * 设置错误信息
     * @param message 错误消息
     */
    public BusinessException(String message) {
        this.message = message;
    }

    /**
     * 设置错误信息和状态码
     * @param message 错误消息
     * @param code 错误码
     */
    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    /**
     * 设置错误信息、状态码和异常对象
     * @param message 错误消息
     * @param code 错误码
     * @param cause 原始异常对象
     */
    public BusinessException(String message, Integer code, Throwable cause) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    /**
     *  通过枚举设置错误信息
     * @param resultCodeEnum 接收枚举类型
     */
    public BusinessException(ResponseEnum resultCodeEnum) {
        this.message = resultCodeEnum.getMessage();
        this.code = resultCodeEnum.getCode();
    }

    /**
     * 设置错误信息和异常对象
     * @param resultCodeEnum 接收枚举类型
     * @param cause 原始异常对象
     */
    public BusinessException(ResponseEnum resultCodeEnum, Throwable cause) {
        super(cause);
        this.message = resultCodeEnum.getMessage();
        this.code = resultCodeEnum.getCode();
    }

}
