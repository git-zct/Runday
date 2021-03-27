package com.zhu.service.impl;

import com.google.common.collect.Lists;
import com.zhu.common.Const;
import com.zhu.common.ResponseCode;
import com.zhu.common.ServerResponse;
import com.zhu.common.TokenCache;
import com.zhu.dao.BlogMapper;
import com.zhu.dao.PlanMapper;
import com.zhu.dao.UserMapper;
import com.zhu.pojo.Blog;
import com.zhu.pojo.User;
import com.zhu.service.IUserService;
import com.zhu.utils.MD5Util;
import com.zhu.vo.UserBlogVo;
import com.zhu.vo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Resource
    UserMapper userMapper;
    @Resource
    PlanMapper planMapper;
    @Resource
    BlogMapper blogMapper;

    @Override
    public ServerResponse login(String username, String password) {
        int rowCount = userMapper.checkUsername(username);
        if(rowCount==0){
            return ServerResponse.createByErrorMessage("学生不存在");
        }
        String MD5pass=MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, MD5pass);
        if (user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),"密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    public ServerResponse<String> register(User user){
        if(user==null){
            return ServerResponse.createByErrorMessage("参数为空");
        }
        if(user.getProfilePic()==null){user.setProfilePic("http://47.111.157.72/images/defaultProfilePic.jpg");}
        user.setScores(0);
        user.setFans(0);
        user.setRank(0);
        user.setFollowing(0);
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getPhone(), Const.PHONE);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

       user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
      //  System.out.println("这是数据："+user.getAnswer());
        int resultCount = userMapper.insert(user);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        else{
            return ServerResponse.createBySuccessMessage("注册成功");
        }


    }

    public ServerResponse<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            if(Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount != 0) {
                    return ServerResponse.createByErrorMessage("用户已经存在");
                }
            }
                if(Const.PHONE.equals(type)){
                    int resultCount=userMapper.checkPhone(str);
                    if(resultCount>0){

                        return ServerResponse.createByErrorMessage("手机号已经存在");
                    }
                }
            }else{
                return ServerResponse.createByErrorMessage("参数错误");
            }

        return ServerResponse.createBySuccess("校验成功");
    }

    public  ServerResponse<User> updateInformation(User user){
        int resultCount=userMapper.checkPhoneById(user.getPhone(),user.getId());
     //   System.err.println(user.getId()+"hhhh"+resultCount);
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("手机号已经被别人使用，请更换手机再尝试更新");
        }
        User updateUser=new User();
        updateUser.setId(user.getId());
        updateUser.setPhone(user.getPhone());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setDescription(user.getDescription());

        int updateCount=userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount>0){
            User user1 = userMapper.selectByPrimaryKey(user.getId());
            return ServerResponse.createBySuccess("更新个人信息成功",user1);
        }

        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }



    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权，要校验一下这个用户的旧密码一定是这个用户,因为我们会查询count，如果不指定Id,可能用户密码不匹配
        int resultCount=userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount=userMapper.updateByPrimaryKeySelective(user);

        if(updateCount>0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }

        return ServerResponse.createByErrorMessage("密码感谢失败");

    }

    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse=this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户名不存在、
            return  ServerResponse.createBySuccessMessage("用户不存在");
        }
        String question =userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");

    }


    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount=userMapper.checkAnswer(username,question,answer);
        System.out.println(resultCount+"resultCount");
        if(resultCount>0){
            //说明问题和问题答案是这个用户的，并且是正确的
            String forgetToken= UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }


    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，token需要传递");
        }
        ServerResponse validResponse=this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户名不存在、
            return  ServerResponse.createBySuccessMessage("用户不存在");
        }

        String token=TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return  ServerResponse.createByErrorMessage("token无效或者过期");
        }

        if(StringUtils.equals(token,forgetToken)){
            String md5Password=MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount=userMapper.updatePasswordByUsername(username,md5Password);
            if(rowCount>0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<User> changeProfilePic(Integer userId,String targetFileName) {
        int count = userMapper.changeProfilePic(userId, targetFileName);
        if(count==0){
            return ServerResponse.createByErrorMessage("换头像失败");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        return ServerResponse.createBySuccess(user);
    }

    public ServerResponse getInformation(User user){
        int resultCount = userMapper.checkUserById(user.getId());
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("该用户已经注销");
        }

        List<Blog> blogList = blogMapper.selectByUserId(user.getId());
        UserBlogVo userBlogVo =new UserBlogVo();
        userBlogVo.setBlogList(blogList);
        if(user.getUsername()==null||user.getProfilePic()==null||user.getScores()==null){  user=userMapper.selectByPrimaryKey(user.getId());}

        userBlogVo.setId(user.getId());
        userBlogVo.setUsername(user.getUsername());
        userBlogVo.setProfilePic(user.getProfilePic());
        userBlogVo.setScores(user.getScores());

        return ServerResponse.createBySuccess(userBlogVo);
    }

    public ServerResponse<User> selectInfo(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            return ServerResponse.createByErrorMessage("找不到？");
        }
        return ServerResponse.createBySuccess(user);
    }


   public ServerResponse selectUserByName(String username){
       List<User> userList = userMapper.selectUserByName(username);
       List<UserVo> userVoList = Lists.newArrayList();
       for(User user:userList){
           UserVo userVo=new UserVo();
           userVo.setId(user.getId());
           userVo.setUsername(user.getUsername());
           userVo.setProfilePic(user.getProfilePic());
           userVo.setFans(user.getFans());
           userVoList.add(userVo);
       }
       return ServerResponse.createBySuccess(userVoList);
   }

}
