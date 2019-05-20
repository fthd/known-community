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

    private Integer allowPost;

    private String showInBbs;

    private String showInQuestion;

    private String showInKnowledge;

    private String showInExam;
    
    private Integer count;
    
    private Integer todayCount;
    
    List<Category> children = new ArrayList<Category>();


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public void setShowInBbs(String showInBbs) {
        this.showInBbs = showInBbs == null ? null : showInBbs.trim();
    }


    public void setShowInQuestion(String showInQuestion) {
        this.showInQuestion = showInQuestion == null ? null : showInQuestion.trim();
    }


    public void setShowInKnowledge(String showInKnowledge) {
        this.showInKnowledge = showInKnowledge == null ? null : showInKnowledge.trim();
    }

    public void setShowInExam(String showInExam) {
        this.showInExam = showInExam == null ? null : showInExam.trim();
    }
    
}