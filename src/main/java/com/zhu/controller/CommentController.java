package com.zhu.controller;

import com.zhu.common.Const;
import com.zhu.common.ServerResponse;
import com.zhu.pojo.Comment;
import com.zhu.pojo.User;
import com.zhu.service.ICommentService;
import com.zhu.service.IFileService;
import com.zhu.utils.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/comment/")
public class CommentController {

    @Resource
    ICommentService commentService;

    @Resource
    IFileService fileService;



    @RequestMapping("add_comm")
    @ResponseBody
    public ServerResponse addComm(HttpSession session, Comment comment, @RequestParam(value = "pic",required = false) MultipartFile file, HttpServletRequest request){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        System.out.println(file+"**");
        if(file!=null) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.uploadCode(file, path,Const.Path.commentPic);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + "commentPic/" + targetFileName;
            comment.setImage(url);
            System.err.println(comment.getImage());
        }
        return   commentService.addComm(user.getId(),comment);
    }

    @RequestMapping("list_comm")
    @ResponseBody
    public ServerResponse listComm(Integer blogId){

        return   commentService.listComm(blogId);
    }
    @RequestMapping("delete_comm")
    @ResponseBody
    public ServerResponse deleteComm(Integer blogId,Integer commId){

        return   commentService.deleteComm(blogId,commId);
    }



}
