package com.zhu.controller;

import com.google.common.collect.Maps;
import com.zhu.common.ServerResponse;
import com.zhu.service.IFileService;
import com.zhu.utils.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class FileUploadController {

    @Resource
    IFileService fileService;

    @RequestMapping("/upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        String path=request.getSession().getServletContext().getRealPath("upload");
        String targetFileName=fileService.upload(file,path);
        String url= PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }
}
