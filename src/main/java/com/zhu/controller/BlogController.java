package com.zhu.controller;

import com.zhu.common.Const;
import com.zhu.common.ServerResponse;
import com.zhu.pojo.Blog;
import com.zhu.pojo.User;
import com.zhu.service.IBlogService;
import com.zhu.service.IFileService;
import com.zhu.utils.PropertiesUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Resource
    IBlogService blogService;
    @Resource
    IFileService fileService;

    @RequestMapping("add_blog")
    @ResponseBody
    public ServerResponse addBlog(HttpSession session,Blog blog,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(file!=null) {
            System.err.println("有文件");
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.uploadCode(file, path, Const.Path.blogPic);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + "blogPic/" + targetFileName;
            blog.setImage(url);
        }
        return  blogService.addBlog(user.getId(),blog);
    }






    @RequestMapping("delete_blog")
    @ResponseBody
    public ServerResponse deleteBlog(HttpSession session,Integer id){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return  blogService.deleteBlog(user.getId(),id);
    }
    @RequestMapping("list_blog")
    @ResponseBody
    public ServerResponse listBlog(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return  blogService.listBlog(user.getId());
    }
//    @RequestMapping("star_blog")
//    @ResponseBody
//    public ServerResponse starBlog(HttpSession session,int blogId){
//        User user=(User)session.getAttribute(Const.CURRENT_USER);
//        if(user==null){
//            return ServerResponse.createByErrorMessage("用户未登录");
//        }
//        return  blogService.starBlog(user.getId());
//    }

    @RequestMapping("list_blog_comm")
    @ResponseBody
    public ServerResponse listBlogAndComm(HttpSession session,Integer blogId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
       return blogService.listBlogAndComm(blogId);
    }

    @RequestMapping("world")
    @ResponseBody
    public ServerResponse world(@RequestParam(value = "TimeLimit",required = false,defaultValue = "7")Integer TimeLimit){
        System.err.println(TimeLimit);
        return blogService.world(TimeLimit);
    }


    @RequestMapping("select_blog_keyword")
    @ResponseBody
    public ServerResponse selectBlogByKeyWord(String keyWord){
        return blogService.selectBlogs(keyWord);
    }

    @RequestMapping("star_blog")
    @ResponseBody
    public ServerResponse starBlog(HttpSession session,Integer blogId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return blogService.starBlog(blogId);
    }

    @RequestMapping("unstar_blog")
    @ResponseBody
    public ServerResponse unStarBlog(HttpSession session,Integer blogId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return blogService.unStarBlog(blogId);
    }
//获取
    @RequestMapping("get_hot_blog")
    @ResponseBody
    public ServerResponse getHotBlog(@RequestParam(value = "TimeLimit",required = false,defaultValue = "7")Integer TimeLimit
    ,@RequestParam(value = "CountLimit",required = false,defaultValue = "5")Integer CountLimit){
       return blogService.getHotBlogs(TimeLimit,CountLimit);
    }

}
