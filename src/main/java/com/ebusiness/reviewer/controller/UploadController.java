package com.ebusiness.reviewer.controller;

import com.ebusiness.reviewer.service.UploadService;
import com.ebusiness.reviewer.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * @author husheng
 * @date 2021/5/30 - 18:53
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/")
    public RespBean uploadFile(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws FileNotFoundException {
        return uploadService.uploadFile(req,multiReq);
    }
}
