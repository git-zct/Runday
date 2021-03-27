package com.zhu.service;

import com.zhu.common.ServerResponse;
import com.zhu.pojo.Blog;

public interface IBlogService {
    ServerResponse addBlog(Integer userId,Blog blog);
    ServerResponse listBlog(Integer userId);
    ServerResponse deleteBlog(Integer userId,Integer id);
//    ServerResponse starBlog();
   ServerResponse listBlogAndComm(Integer blogId);
   ServerResponse world(Integer TimeLimit);
    ServerResponse selectBlogs(String keyWord);
    ServerResponse starBlog(Integer blogId);
    ServerResponse unStarBlog(Integer blogId);

    ServerResponse  getHotBlogs(int TimeLimit,int CountLimit);
}
