package cn.lihongjie.pdfservice.dto;

import lombok.Data;

import java.util.*;

@Data
public class GenFileResponse {

    private String base64File;

    private Map<String, Long> metrics;

}
