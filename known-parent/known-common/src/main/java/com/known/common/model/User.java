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
    
    private Integer userPage = 0;
    
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setUserBg(String userBg) {
        this.userBg = userBg == null ? null : userBg.trim();
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public void setCharacters(String characters) {
        this.characters = characters == null ? null : characters.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public void setWork(String work) {
        this.work = work == null ? null : work.trim();
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode == null ? null : activationCode.trim();
    }
    
	public String getBirthdayString() {
		if(birthday == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(birthday));
	}

	public String getRegisterTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(registerTime));
	}

	public String getLastLoginTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(lastLoginTime));
	}
    
}