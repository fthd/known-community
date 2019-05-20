package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThirdUser {

    private String account;// 用户
    private String userName;// 用户昵称
    private String avatarUrl;// 用户头像地址
    private String gender;// 用户性别
    private String token;// 用户认证
    private String openid;// 用户第三方id
    private String provider;// 用户类型
    private Integer userId;// 用户id

}
