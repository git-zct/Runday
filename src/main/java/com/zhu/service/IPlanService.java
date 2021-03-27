package com.zhu.service;

import com.zhu.common.ServerResponse;
import com.zhu.pojo.Plan;
import com.zhu.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPlanService {
    ServerResponse add(User user, Plan plan);
    ServerResponse<List<Plan>> list(Integer userId);
    ServerResponse<List<Plan>> listUnDo(Integer userId);
    ServerResponse<String> finishPlan(Integer user_id, Integer plan_id);
}
