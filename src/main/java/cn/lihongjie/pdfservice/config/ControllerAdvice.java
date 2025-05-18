package cn.lihongjie.pdfservice.config;

import cn.lihongjie.pdfservice.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public R<String> handleException(Exception e) {

        log.info("系统异常: {}", e.getMessage(), e);

        return new R<>("error", "系统异常", "");
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R<String> handleIllegalArgumentException(IllegalArgumentException e) {

        log.info("参数异常: {}", e.getMessage(), e);

        return new R<>("argument_error", e.getMessage(), "");
    }



}
