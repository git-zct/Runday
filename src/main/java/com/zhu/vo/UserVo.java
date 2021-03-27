package com.zhu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    public int id;
    public String  username;
    public String profilePic;
    public Integer fans;
}
