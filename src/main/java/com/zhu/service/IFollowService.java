package com.zhu.service;

import com.github.pagehelper.PageInfo;
import com.zhu.common.ServerResponse;

public interface IFollowService {
    ServerResponse addFollow(Integer fansId,Integer userId);
    ServerResponse<PageInfo> listFans(Integer userId);
    ServerResponse listIdol(Integer userId);
}
