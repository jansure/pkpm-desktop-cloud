package com.pkpm.fastdfs.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pkpm.fastdfs.entity.Result;
import com.pkpm.fastdfs.util.ResultUtil;

/**
 * @author Chenjing
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultHandler(Exception e) {
        log.error("global error:{}", e);
        return ResultUtil.failed(e.getMessage());
    }
}
