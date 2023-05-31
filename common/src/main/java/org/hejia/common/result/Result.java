package org.hejia.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果类
 */
@Data
public class Result {

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    /**
     * 构造器私有
     */
    private Result(){}

    /**
     * 返回成功
     */
    public static Result success() {
        Result result = new Result();
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMessage(ResponseEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 返回失败
     */
    public static Result error() {
        Result result = new Result();
        result.setCode(ResponseEnum.ERROR.getCode());
        result.setMessage(ResponseEnum.ERROR.getMessage());
        return result;
    }

    /**
     * 设置特定结果
     */
    public static Result setResult(ResponseEnum response) {
        Result result = new Result();
        result.setCode(response.getCode());
        result.setMessage(response.getMessage());
        return result;
    }

    /**
     * 设置结果信息
     */
    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 设置结果状态码
     */
    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 设置操作结果
     * @param key 键
     * @param value 结果值
     * @return 统一结果
     */
    public Result data(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

    /**
     * 设置结果集
     * @param data 结果数据
     * @return 统一结果
     */
    public Result data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

}
