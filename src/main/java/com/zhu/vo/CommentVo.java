package com.zhu.vo;

import com.zhu.pojo.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    public String username;
    public String profilePic;
    Comment comment;
}
