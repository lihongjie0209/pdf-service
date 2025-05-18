package cn.lihongjie.pdfservice.enums;

import java.util.*;

public enum Metrics {


    GET_BROWSER("获取浏览器对象"),
    NEW_CONTEXT("新建上下文"),
    NEW_PAGE("新建页面"),
    LOAD_PAGE("加载页面"),
    SCREENSHOT("截图"),
    GET_PDF("获取PDF"),
    CLEAN_UP("清理上下文"),
    TOTAL_TIME("全部时间"),

    ;

    private String description;

    Metrics(String description) {
        this.description = description;
    }

}
