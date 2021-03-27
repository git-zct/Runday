package com.zhu.dao;

import com.zhu.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);
//    自己
    List<Blog> selectByUserId(Integer userId);
    int checkUser(@Param("userId") Integer userId,@Param("blogId") Integer blogId);
    List<Blog> getAll();
    List<Blog> selectByKeyWord(String keyWord);
}