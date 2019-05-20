package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogCategory {
    private Integer categoryId;

    private Integer userId;

    private String name;

    private Integer rank;
    
    private Integer blogCount;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


}