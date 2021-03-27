package com.zhu.service.impl;

import com.zhu.common.ServerResponse;
import com.zhu.dao.PlanMapper;
import com.zhu.dao.UserMapper;
import com.zhu.pojo.Plan;
import com.zhu.pojo.User;
import com.zhu.service.IPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("iPlanService")
public class PlanServiceImpl implements IPlanService {
    @Resource
    PlanMapper planMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public ServerResponse add(User user, Plan plan) {
        if(plan==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        if(userMapper.checkUsername(user.getUsername())==0){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        plan.setUserId(user.getId());
        plan.setChecked(0);
        int resultCount = planMapper.insert(plan);
        if(resultCount>0){
            return this.listUnDo(user.getId());
        }
        return ServerResponse.createByErrorMessage("添加计划失败");
    }

    public ServerResponse<List<Plan>> list(Integer userId){
        List<Plan> allPlanById = planMapper.getAllPlanById(userId);
        return ServerResponse.createBySuccess(allPlanById);
    }
    public ServerResponse<List<Plan>> listUnDo(Integer userId){
        List<Plan> allPlanById = planMapper.getAllUnDoPlanById(userId);
        if(allPlanById!=null) {
            return ServerResponse.createBySuccess(allPlanById);
        }else{
            return ServerResponse.createByErrorMessage("暂无运动计划哦！");
        }
    }

    public ServerResponse<String> finishPlan(Integer userId,Integer planId){
        Plan plan = planMapper.selectByPrimaryKey(planId);
        if(plan==null){
            return ServerResponse.createByErrorMessage("暂无这个运动计划");
        }
        if(plan.getChecked()==1){
            return ServerResponse.createByErrorMessage("这个运动计划你已经达成了！");
        }
        int resultCount= planMapper.finishPlan(userId, planId);
        int thisScore=plan.getScore();
        User user=userMapper.selectByPrimaryKey(userId);
        user.setScores(user.getScores()+thisScore);
        user.setId(userId);
        int resultCount2 = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount>0 && resultCount2>0){
            return ServerResponse.createBySuccessMessage("刚才您完成计划:"+plan.getTarget()+"; 你获得了"+plan.getScore()+
                    "分，太棒啦😁！  目前分数"+user.getScores());
        }else{
            return ServerResponse.createByErrorMessage("服务器未将计划设置成完成状态，请联系管理员");
        }
    }


}
