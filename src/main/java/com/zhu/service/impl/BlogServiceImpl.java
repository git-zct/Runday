package com.zhu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhu.common.ServerResponse;
import com.zhu.dao.BlogMapper;
import com.zhu.dao.CommentMapper;
import com.zhu.dao.UserMapper;
import com.zhu.pojo.Blog;
import com.zhu.pojo.Comment;
import com.zhu.pojo.User;
import com.zhu.service.IBlogService;
import com.zhu.vo.BlogAndCommVo;
import com.zhu.vo.BlogVo;
import com.zhu.vo.CommentVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("iBlogService")
public class BlogServiceImpl implements IBlogService {
    @Resource
    BlogMapper blogMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    CommentMapper commentMapper;

    public ServerResponse addBlog(Integer userId,Blog blog){
        blog.setUserId(userId);
        blog.setStars(0);
        int retCount = blogMapper.insert(blog);
        if(retCount==0){
            return ServerResponse.createByErrorMessage("发布动态失败");
        }
        return this.listBlog(userId);
    }

    public ServerResponse listBlog(Integer userId){

        List<Blog> blogList = blogMapper.selectByUserId(userId);
        if(blogList==null){
            return ServerResponse.createByErrorMessage("暂无动态");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        List<BlogVo> blogVoList= Lists.newArrayList();
        for(Blog blog:blogList){
            BlogVo blogVo=new BlogVo();
            blogVo.setUsername(user.getUsername());
            blogVo.setProfilePic(user.getProfilePic());
            blogVo.setBlog(blog);
            blogVoList.add(blogVo);
        }
        return ServerResponse.createBySuccess(blogVoList);
    }

    public ServerResponse deleteBlog(Integer userId,Integer blogId){
        //防止横向越权，删除不是自己的动态
        int checkIs = blogMapper.checkUser(userId, blogId);
        if(checkIs==0){
            return ServerResponse.createByErrorMessage("试图删除别人的动态，横向越权了");
        }
        int resultCount = blogMapper.deleteByPrimaryKey(blogId);
        if(resultCount>0){
            return  ServerResponse.createBySuccessMessage("删除动态成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }


    public ServerResponse listBlogAndComm(Integer blogId){
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        if(blog==null){
            return ServerResponse.createByErrorMessage("找不到这个动态");
        }
        Integer userId = blog.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            return ServerResponse.createByErrorMessage("改用户已经注销");
        }
        List<Comment> comments = commentMapper.selectAllcommByBlogId(blogId);
        BlogAndCommVo blogAndCommVo=new BlogAndCommVo();
        blogAndCommVo.setId(userId);
        blogAndCommVo.setPhone(user.getPhone());
        blogAndCommVo.setUsername(user.getUsername());
        blogAndCommVo.setBlog(blog);

        List<CommentVo> commentVoList=Lists.newArrayList();
        for(Comment comment:comments){
            // todo 这要查的是谁评论的
            userMapper.selectByPrimaryKey(comment.getUserId());

                CommentVo commentVo=new CommentVo();
                commentVo.setUsername(user.getUsername());
                commentVo.setProfilePic(user.getProfilePic());
                commentVo.setComment(comment);
                commentVoList.add(commentVo);
        }
        blogAndCommVo.setCommentList(commentVoList);
        return ServerResponse.createBySuccess(blogAndCommVo);

    }

    public ServerResponse world(Integer TimeLimit){
        PageHelper.startPage(/*pageNum*/10,/*pageSize*/10);
        List<Blog> blogList = blogMapper.getAll();
        List<Blog> RecentBlogList= Lists.newArrayList();
        Date createTime;
        Date currentTime=new Date();

        List<BlogVo> blogVoList=Lists.newArrayList();
        for (Blog blog:blogList){
             createTime = blog.getCreateTime();

//           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//           System.err.println("current"+df.format(currentTime));
//            System.err.println(df.format(createTime));
 //           System.err.println("year"+createTime.getYear()+1900+"month"+createTime.getMonth()+1+"day:"+createTime.getDate());


            //返回最近一周的动态
            if(createTime.getYear()==createTime.getYear()&&
                    currentTime.getMonth()==createTime.getMonth()&&
                    currentTime.getDate()-createTime.getDate()<=TimeLimit) {
                System.err.println(currentTime.getDate()-createTime.getDate()+"哈哈哈"+createTime.getDate()+"asd"+currentTime.getDate());

                User user = userMapper.selectByPrimaryKey(blog.getUserId());
                BlogVo blogVo=new BlogVo();
                blogVo.setUsername(user.getUsername());
                blogVo.setProfilePic(user.getProfilePic());
                blogVo.setBlog(blog);
                blogVoList.add(blogVo);
           }else{
                continue;
            }
        }
        PageInfo pageResult = new PageInfo(blogVoList);
        pageResult.setList(blogVoList);
       return ServerResponse.createBySuccess(pageResult);
    }



    public ServerResponse selectBlogs(String keyWord){
        List<Blog> blogList = blogMapper.selectByKeyWord(keyWord);
        if(blogList.isEmpty()){
            return ServerResponse.createByErrorMessage("找不到相关动态");
        }
        List<BlogVo> blogVoList=Lists.newArrayList();
        for(Blog blog:blogList){
            User user = userMapper.selectByPrimaryKey(blog.getUserId());
            BlogVo blogVo=new BlogVo();
            blogVo.setBlog(blog);
            blogVo.setUsername(user.getUsername());
            blogVo.setProfilePic(user.getProfilePic());
            blogVoList.add(blogVo);
        }
        return ServerResponse.createBySuccess(blogVoList);
    }

    public ServerResponse starBlog(Integer blogId){
        Blog blog=blogMapper.selectByPrimaryKey(blogId);
        if(blog==null){
            return ServerResponse.createByError();
        }
        blog.setStars(blog.getStars()+1);
        int count = blogMapper.updateByPrimaryKeySelective(blog);
        if(count>0){
            return ServerResponse.createBySuccess("谢谢你的鼓励！");
        }else{
            return ServerResponse.createByErrorMessage("点赞失败，服务器问题");
        }
    }
    public ServerResponse unStarBlog(Integer blogId){
        Blog blog=blogMapper.selectByPrimaryKey(blogId);
        if(blog==null){
            return ServerResponse.createByError();
        }
        blog.setStars(blog.getStars()-1);
        int count = blogMapper.updateByPrimaryKeySelective(blog);
        if(count>0){
            return ServerResponse.createBySuccess("取消点赞！");
        }else{
            return ServerResponse.createByErrorMessage("取消失败,服务器问题");
        }
    }

  public ServerResponse  getHotBlogs(int TimeLimit,int CountLimit){
      List<Blog> blogList = blogMapper.getAll();
      List<Blog> RecentBlogList= Lists.newArrayList();
      Date createTime;
      Date currentTime=new Date();

      List<BlogVo> blogVoList=Lists.newArrayList();
      int count=0;
      for (Blog blog:blogList){
          if(count>=CountLimit){//烂代码
              count=0;
              break;
          }
          createTime = blog.getCreateTime();

          //返回最近一周的动态
          if(createTime.getYear()==createTime.getYear()&&
                  currentTime.getMonth()==createTime.getMonth()&&
                  currentTime.getDate()-createTime.getDate()<=TimeLimit) {
            //  System.err.println(currentTime.getDate()-createTime.getDate()+"哈哈哈"+createTime.getDate()+"asd"+currentTime.getDate());

              User user = userMapper.selectByPrimaryKey(blog.getUserId());
              BlogVo blogVo=new BlogVo();
              blogVo.setUsername(user.getUsername());
              blogVo.setProfilePic(user.getProfilePic());
              blogVo.setBlog(blog);
              count++;
              blogVoList.add(blogVo);
          }else{
              continue;
          }

      }
      return ServerResponse.createBySuccess(blogVoList);
  }

}
