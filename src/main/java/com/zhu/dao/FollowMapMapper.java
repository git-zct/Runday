package com.zhu.dao;

import com.zhu.pojo.FollowMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FollowMap record);

    int insertSelective(FollowMap record);

    FollowMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FollowMap record);

    int updateByPrimaryKey(FollowMap record);
//
    int check(@Param("userId")Integer userId,@Param("fansId")Integer fansId);
    int deleteByfan(@Param("userId")Integer userId,@Param("fansId")Integer fansId);
    List<FollowMap> selectAllFans(Integer userId);
    List<FollowMap> selecIdols(Integer fansId);
}