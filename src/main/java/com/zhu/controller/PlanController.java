package com.zhu.controller;

import com.zhu.common.Const;
import com.zhu.common.ServerResponse;
import com.zhu.pojo.Plan;
import com.zhu.pojo.User;
import com.zhu.service.IPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/plan")
public class PlanController {

    @Resource
    IPlanService planService;

    @RequestMapping("add_plan")
    @ResponseBody
    public ServerResponse addPlan(HttpSession session, Plan plan){

        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return planService.add(user,plan);
    }

    @RequestMapping("list_plan")
    @ResponseBody
    public ServerResponse listPlan(HttpSession session){

        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return planService.list(user.getId());
    }
    @RequestMapping("list_undo_plan")
    @ResponseBody
    public ServerResponse listUnDoPlan(HttpSession session){

        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return planService.listUnDo(user.getId());
    }

    @RequestMapping("finish_plan")
    @ResponseBody
    public ServerResponse finishPlan(HttpSession session,Integer plan_id){

        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return planService.finishPlan(user.getId(),plan_id);
    }


}
