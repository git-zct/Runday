package com.zhu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FansVo {
    int id;
    String username;
    int intimacy;
    String profilePic;
}
