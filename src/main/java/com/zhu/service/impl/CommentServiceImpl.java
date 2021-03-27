package com.zhu.service.impl;

import com.zhu.common.ServerResponse;
import com.zhu.dao.CommentMapper;
import com.zhu.pojo.Comment;
import com.zhu.service.IBlogService;
import com.zhu.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("iCommentService")
public class CommentServiceImpl implements ICommentService {

    @Resource
    CommentMapper commentMapper;
    @Resource
    IBlogService blogService;

    @Override
    public ServerResponse addComm(Integer userId, Comment comment) {
        if(comment==null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        comment.setUserId(userId);
        int resultCount = commentMapper.insert(comment);
        if(resultCount>0){
//            todo 显示动态和当前所有评论
            ServerResponse serverResponse = blogService.listBlogAndComm(comment.getBlogId());
            return ServerResponse.createBySuccess("评论成功",serverResponse);
        }
        return ServerResponse.createByErrorMessage("评论失败，联系管理员");
    }

    @Override
    public ServerResponse<List<Comment>> listComm(Integer blogId) {
        List<Comment> commentList = commentMapper.selectAllcommByBlogId(blogId);
        return ServerResponse.createBySuccess(commentList);
    }

    @Override
    public ServerResponse deleteComm(Integer blogId, Integer commId) {
        int i = commentMapper.deleteByPrimaryKey(commId);
        if(i>0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("删除评论失败");
    }
}
