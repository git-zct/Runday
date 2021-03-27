package com.zhu.dao;

import com.zhu.pojo.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Plan record);

    int insertSelective(Plan record);

    Plan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Plan record);

    int updateByPrimaryKey(Plan record);
//    自己
    List<Plan> getAllPlanById(Integer userId);
    List<Plan> getAllUnDoPlanById(Integer userId);
    int finishPlan(@Param("userId") Integer userId,@Param("planId") Integer planId);
    List<Plan>  selectByUserId(Integer userId);

}