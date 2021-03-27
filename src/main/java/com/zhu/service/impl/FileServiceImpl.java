package com.zhu.service.impl;

import com.google.common.collect.Lists;
import com.zhu.common.Const;
import com.zhu.service.IFileService;
import com.zhu.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            ArrayList<File> files = Lists.newArrayList(targetFile);

           FTPUtil.uploadFile(files);
            //已经上传到ftp服务器上

            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        //A:abc.jpg
        //B:abc.jpg
        return targetFile.getName();
    }

    @Override
    public String uploadCode(MultipartFile file, String path, Integer pathCode) {
        String fileName=file.getOriginalFilename();
        String fileExtensionName=fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName= UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            ArrayList<File> files = Lists.newArrayList(targetFile);

            FTPUtil.uploadFile(files,pathCode);
            //已经上传到ftp服务器上

            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        //A:abc.jpg
        //B:abc.jpg
        return targetFile.getName();
    }

}
