package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.utils.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private Integer userid;

    private String email;

    private String userName;

    private String password;

    private String userIcon;

    private String userBg;

    private String age;

    private String sex;

    private String characters;

    private Integer mark=0;

    private String address;

    private String work;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    
    private String birthdayString;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date registerTime;

    private String registerTimeString;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date lastLoginTime;

    private String lastLoginTimeString ;
    
    private String activationCode;

    private Integer status;

    private Integer userPage;
    
}