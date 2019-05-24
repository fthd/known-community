package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    private Integer categoryId;

    private Integer pid;

    private String name;

    private String desc;

    private Integer rank;

    private String showInTopic;

    private String showInAsk;

    private String showInKnowledge;
    
    private Integer count;
    
    private Integer todayCount;
    
    List<Category> children = new ArrayList<>();


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public void setShowInTopic(String showInTopic) {
        this.showInTopic = showInTopic == null ? null : showInTopic.trim();
    }


    public void setShowInAsk(String showInAsk) {
        this.showInAsk = showInAsk == null ? null : showInAsk.trim();
    }


    public void setShowInKnowledge(String showInKnowledge) {
        this.showInKnowledge = showInKnowledge == null ? null : showInKnowledge.trim();
    }

}