package com.known.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.known.common.model.SysRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserVo {
    private Integer userid;

    private String email;

    private String userName;

    private String userIcon;

    private String age;

    private String sex;

    private String characters;

    private Integer mark=0;

    private String address;

    private String work;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date registerTime;

    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastLoginTime;
    
    
    private List<SysRole> rolesList;
    
}
