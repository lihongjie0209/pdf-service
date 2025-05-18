package cn.lihongjie.pdfservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private String code;

    private String msg;

    private T data;

}
