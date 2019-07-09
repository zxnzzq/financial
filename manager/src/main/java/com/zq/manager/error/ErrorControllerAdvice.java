package com.zq.manager.error;

import com.zq.entity.enums.ErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = {"com.zq.manager.controller"})
public class ErrorControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        String message = e.getMessage();
        ErrorEnum errorEnum = ErrorEnum.getByMessage(message);
        String code = errorEnum.getCode();
        boolean canRetry = errorEnum.isCanRetry();
        map.put("code", code);
        map.put("message", message);
        map.put("canRetry", canRetry);
        return new ResponseEntity(map, HttpStatus.I_AM_A_TEAPOT);
    }
}
