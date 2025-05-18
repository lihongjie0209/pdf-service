package cn.lihongjie.pdfservice.dto;

import lombok.Data;

import java.util.*;


@Data
public abstract class BaseRequest {

    private String url;

    private String html;


    private Integer timeout;


    private String browserType;

    private Integer screenWidth;

    private Integer screenHeight;




}
