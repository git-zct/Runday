package com.zhu.dao;

import com.zhu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
//    ziji
    int checkUsername(String username);
    User selectLogin(@Param("username") String username, @Param("password") String password);
    int checkPhone(String phone);
    int checkPhoneById(@Param("phone") String phone,@Param("id") Integer id);
    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);
    String selectQuestionByUsername(String username);
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);
    int updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
    int changeProfilePic(@Param("userId") Integer userId,@Param("profile_picture") String targetFileName);
    int checkUserById(int id);
    List<User> selectUserByName(String  username);
}