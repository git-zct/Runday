package com.zhu.controller;

import com.zhu.common.Const;
import com.zhu.common.ServerResponse;
import com.zhu.pojo.User;
import com.zhu.service.IFollowService;
import com.zhu.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/follow/")
public class followController {

    @Resource
    IFollowService followService;
    @Resource
    IUserService userService;

    //模糊查询用户
    @RequestMapping("select_user")
    @ResponseBody
    public ServerResponse selectUser(HttpSession session,String username){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.selectUserByName(username);
    }

    @RequestMapping("get_homepage")
    @ResponseBody
    public ServerResponse getHomepage(HttpSession session,Integer userId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        User userWantKnow=new User();
        userWantKnow.setId(userId);
        return   userService.getInformation(userWantKnow);
    }

    @RequestMapping("add")
    @ResponseBody
    public ServerResponse addFollower(HttpSession session,Integer userId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
      return   followService.addFollow(user.getId(),userId);
    }

    @RequestMapping("list_fans")
    @ResponseBody
    public ServerResponse listFans(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return   followService.listFans(user.getId());
    }
    @RequestMapping("list_idol")
    @ResponseBody
    public ServerResponse listIdol(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return   followService.listIdol(user.getId());
    }

}
