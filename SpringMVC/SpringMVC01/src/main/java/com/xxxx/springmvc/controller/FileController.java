package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    @RequestMapping("uploadFile")
    public String uploadFile(HttpServletRequest request){
        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;

        MultipartFile mf = mhsr.getFile("file");

        try {
            if(null != mf&&mf.getSize()>0) {
    //            项目所在地址绝对路径
    //            获取上下文后才能操作
                String basePath = request.getSession().getServletContext().getRealPath("/");
    //            创建文件夹
                File upload = new File(basePath+"/upload");
                if(!(upload.exists())){
                    upload.mkdir();//创建

                }
    //            文件名设置
                String filename = System.currentTimeMillis() + mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
    //        copy
                mf.transferTo(new File(upload,filename));
                request.setAttribute("msg","File has been output");
            }else{
                request.setAttribute("msg","not success");
            }
        } catch (Exception e) {
            request.setAttribute("msg","not success");
            e.printStackTrace();
        }
        return "result";
    }
}
