package com.zhu.service;

import com.zhu.common.ServerResponse;
import com.zhu.pojo.User;

public interface IUserService {
    ServerResponse login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<User> updateInformation(User user);
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);
    ServerResponse<String> checkAnswer(String username, String question, String answer);
    ServerResponse selectQuestion(String username);
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
    ServerResponse changeProfilePic(Integer userId,String targetFileName);
    ServerResponse getInformation (User user);
    ServerResponse<User> selectInfo(Integer userId);
    ServerResponse selectUserByName(String username);
}
