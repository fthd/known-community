package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private String id;

    private String taskClassz;

    private String taskMethod;

    private String taskTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastUpdateTime;

    private Integer status;

    private String des;

    public void setTaskClassz(String taskClassz) {
        this.taskClassz = taskClassz == null ? null : taskClassz.trim();
    }

    public void setTaskMethod(String taskMethod) {
        this.taskMethod = taskMethod == null ? null : taskMethod.trim();
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime == null ? null : taskTime.trim();
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
}