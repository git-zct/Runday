package com.zhu.service;

import com.zhu.common.ServerResponse;
import com.zhu.pojo.Comment;

public interface ICommentService {
    ServerResponse addComm(Integer userId, Comment comment);
    ServerResponse listComm(Integer blogId);
   ServerResponse deleteComm(Integer blogId,Integer commId);
}
