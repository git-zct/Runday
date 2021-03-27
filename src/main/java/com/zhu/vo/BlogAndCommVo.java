package com.zhu.vo;

import com.zhu.pojo.Blog;
import com.zhu.pojo.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndCommVo {
    String phone;
    int id;
    String username;
    Blog blog;
    List<CommentVo> commentList;
}
