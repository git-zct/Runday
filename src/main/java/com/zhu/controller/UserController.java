package com.zhu.controller;

import com.google.common.collect.Maps;
import com.zhu.common.Const;
import com.zhu.common.ServerResponse;
import com.zhu.pojo.User;
import com.zhu.service.IFileService;
import com.zhu.service.IUserService;
import com.zhu.utils.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    IUserService iUserService;

    @Resource
    IFileService fileService;


    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse<User> response= iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping("/register.do")
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }


    @GetMapping("logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @GetMapping("get_user_info.do")
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
           return iUserService.selectInfo(user.getId());
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user){
        User currentUser=(User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
       // System.err.println(user.getId());
       // user.setUsername(currentUser.getUsername());
        ServerResponse<User> response=iUserService.updateInformation(user);
        if(response.isSuccess()){
            System.out.println("ok啦");
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }


    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        System.out.println(username+question+answer);
        return iUserService.checkAnswer(username,question,answer);
    }


    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        System.out.println(username+passwordNew+forgetToken);
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping("changeProfilePic")
    @ResponseBody
    public ServerResponse changeProfilePic(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录，无法修改头像");
        }
       // System.err.println(file);
        String path=request.getSession().getServletContext().getRealPath("upload");
        String targetFileName= fileService.uploadCode(file, path,Const.Path.profilePic);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+"profilePic/"+targetFileName;
//        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri",targetFileName);
//        fileMap.put("url",url);
        session.removeAttribute(Const.CURRENT_USER);
        ServerResponse serverResponse = iUserService.changeProfilePic(user.getId(), url);
        session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        return serverResponse;
    }

    @RequestMapping(value = "get_home")
    @ResponseBody
    public ServerResponse<String> getInformation(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.getInformation(user);
    }

    @RequestMapping(value = "get_information_id")
    @ResponseBody
    public ServerResponse<String> getInformation(Integer userId){
       User user= new User();
       user.setId(userId);
        return iUserService.getInformation(user);
    }




}
