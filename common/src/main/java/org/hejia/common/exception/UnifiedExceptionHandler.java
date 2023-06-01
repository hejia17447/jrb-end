package org.hejia.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.hejia.common.result.ResponseEnum;
import org.hejia.common.result.Result;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 统一异常处理类
 */
@Slf4j
@Component
@RestControllerAdvice
public class UnifiedExceptionHandler {

    /**
     * 未定义异常
     * @param e 异常信息
     * @return 返回异常
     */
    @ExceptionHandler(value = {
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error();
    }

    /**
     * 处理特定异常
     * @param e 异常信息
     * @return 返回异常
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Result handleBadSqlGrammarException(BadSqlGrammarException e) {
        log.error(e.getMessage(), e);
        return Result.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }

    /**
     * 自定义异常处理
     * @param e 异常信息
     * @return 返回异常结果
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return Result.error().message(e.getMessage()).code(e.getCode());
    }

}
