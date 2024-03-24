package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @RequestMapping("uploadFile")
    public String uploadFile(HttpServletRequest request){
        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
        MultipartFile mf = mhsr.getFile("file");
        try {
            if(null !=mf && mf.getSize()>0){
                // 获取项目所在地址(绝对路径)
                String basePath = request.getSession().getServletContext().getRealPath("/");
                File upload = new File(basePath+"/upload");
                if(!(upload.exists())){
                    // 文件夹不存在  创建文件夹
                    upload.mkdir();
                }
                // 设置文件名称  系统当前时间毫秒数 命名上传文件
                // getOriginalFilename() 文件原始文件名
                String fileName = System.currentTimeMillis()+
                        mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf("."));
                // 文件copy
                mf.transferTo(new File(upload,fileName));
                request.setAttribute("msg","文件上传成功!");
            }else{
                request.setAttribute("msg","文件上传失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","文件上传失败!");
        }
        return "result";
    }
}
