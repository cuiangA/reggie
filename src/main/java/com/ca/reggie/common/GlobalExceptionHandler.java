package com.ca.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.info(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[] ms = ex.getMessage().split(" ");
            return R.error("用户名"+ms[2]+"已存在");
        }
        return R.error("添加失败：未知错误");

    }
    @ExceptionHandler(CategoryDeleteException.class)
    public R<String> exceptionHandler(CategoryDeleteException ex){
        log.info(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
