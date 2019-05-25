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

}