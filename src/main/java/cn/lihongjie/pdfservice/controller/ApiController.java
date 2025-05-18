package cn.lihongjie.pdfservice.controller;

import cn.lihongjie.pdfservice.dto.PdfRequest;
import cn.lihongjie.pdfservice.dto.R;
import cn.lihongjie.pdfservice.dto.ScreenShotRequest;
import cn.lihongjie.pdfservice.dto.GenFileResponse;
import cn.lihongjie.pdfservice.service.PlayWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private PlayWriteService playWriteService;

    @PostMapping("/pdf")
    public R<Object> pdf(@RequestBody PdfRequest request) {


        return new R<>("success" , "success", playWriteService.getPdf(request));


    }


    @PostMapping("/screenshot")
    public R<GenFileResponse> screenshot(@RequestBody ScreenShotRequest request) {

        return new R<>("success" , "success", playWriteService.getScreenshot(request));

    }

}
