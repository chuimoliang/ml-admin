package com.moliang.exception;

import com.moliang.model.NorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Use 全局异常处理
 * @Author Chui moliang
 * @Date 2021/2/10 23:37
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public NorResponse handleException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.info("BindException.class");
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return NorResponse.validateFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public NorResponse handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return NorResponse.failed(e.getErrorCode());
        }
        return NorResponse.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public NorResponse handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        log.info("MethodArgumentNotValidException.class");
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return NorResponse.validateFailed(message);
    }
}
