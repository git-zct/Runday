package com.zhu.vo;

import com.zhu.pojo.Blog;
import com.zhu.pojo.Plan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBlogVo {
    private Integer id;
    private String username;
    private String profilePic;
    private Integer scores;
    List<Blog> blogList;
}
