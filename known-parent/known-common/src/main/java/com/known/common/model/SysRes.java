package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRes {
    private Integer id;

    private Integer pid;

    private String name;

    private String des;

    private String url;

    private String iconcls;

    private Integer seq;

    private Integer type;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date modifydate;

    private Integer enabled;

    private Integer level;

    private String key;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }
}