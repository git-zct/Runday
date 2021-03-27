package com.zhu.vo;

import com.zhu.pojo.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogVo {
    public String username;
    public String profilePic;
    Blog blog;
}
