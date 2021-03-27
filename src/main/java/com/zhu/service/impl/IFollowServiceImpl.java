package com.zhu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhu.common.ServerResponse;
import com.zhu.dao.FollowMapMapper;

import com.zhu.dao.UserMapper;
import com.zhu.pojo.FollowMap;
import com.zhu.pojo.User;
import com.zhu.service.IFollowService;
import com.zhu.service.IUserService;
import com.zhu.vo.FansVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service("iFollowService")
public class IFollowServiceImpl implements IFollowService {
    @Resource
    FollowMapMapper followMapMapper;

    @Resource
    UserMapper userMapper;


    @Override
    public ServerResponse addFollow(Integer fansId, Integer userId) {
        if(fansId==userId){
            return ServerResponse.createByErrorMessage("不能关注自己");
        }
        int checkCode = followMapMapper.check(userId, fansId);
        User user = userMapper.selectByPrimaryKey(userId);
        User fan = userMapper.selectByPrimaryKey(fansId);
        if(checkCode==0) {

            if(user==null){
                return ServerResponse.createByErrorMessage("该用户不存在");
            }
            FollowMap followMap = new FollowMap();
            followMap.setUserId(userId);
            followMap.setFollowerId(fansId);
            followMap.setIntimacy(0);
            int i = followMapMapper.insert(followMap);
            if (i > 0) {
                user.setFans(user.getFans()+1);
                fan.setFollowing(fan.getFollowing()+1);
                userMapper.updateByPrimaryKeySelective(user);
                userMapper.updateByPrimaryKeySelective(fan);
                return ServerResponse.createBySuccess();
            }

            return ServerResponse.createByErrorMessage("关注失败");
        }else{
            int i = followMapMapper.deleteByfan(userId, fansId);
            if (i > 0) {
                user.setFans(user.getFans()-1);
                fan.setFollowing(fan.getFollowing()-1);
                userMapper.updateByPrimaryKeySelective(user);
                userMapper.updateByPrimaryKeySelective(fan);
                return ServerResponse.createBySuccess("取消关注");
            }
            return ServerResponse.createByErrorMessage("取消关注失败");

        }
    }
    public ServerResponse listIdol(Integer userId){
        List<FollowMap> followMapList = followMapMapper.selecIdols(userId);
        List<FansVo> fansVoList= Lists.newArrayList();
        for(FollowMap followMap:followMapList){
            FansVo fansVo= assembleFansVo(followMap);
            fansVoList.add(fansVo);
        }
        return ServerResponse.createBySuccess(fansVoList);
    }


    public ServerResponse<PageInfo> listFans(Integer userId){

        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(/*pageNum*/10,/*pageSize*/10);

        List<FollowMap> followMapList = followMapMapper.selectAllFans(userId);
        List<FansVo> fansVoList= Lists.newArrayList();
        for(FollowMap followMap:followMapList){
          FansVo fansVo= assembleFansVo(followMap);
          fansVoList.add(fansVo);
        }

        PageInfo pageResult = new PageInfo(followMapList);
        pageResult.setList(fansVoList);
        return ServerResponse.createBySuccess(pageResult);
    }
    private FansVo assembleFansVo(FollowMap followMap){
        FansVo fansVo=new FansVo();
        User follower = userMapper.selectByPrimaryKey(followMap.getFollowerId());

        fansVo.setId(followMap.getFollowerId());
        fansVo.setUsername(follower.getUsername());
        fansVo.setProfilePic(follower.getProfilePic());
        fansVo.setIntimacy(followMap.getIntimacy());
        return fansVo;
    }
}
