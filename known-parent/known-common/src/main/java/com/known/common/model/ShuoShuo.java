package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.known.common.utils.CustomDateSerializer;
import com.known.common.utils.Emotions;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShuoShuo {

    private String id;

    private String userId;

    private String userIcon;

    private String userName;

    private String imageUrl;

    private String imageUrlSmall;

    private String musicUrl;

    private Date createTime;

    private Integer commentCount;

    private Integer likeCount;

    private String content;

    private String showContent;

    private List<ShuoShuoComment> commentList = new ArrayList<>();

    private List<ShuoShuoLike> shuoShuoLikeList = new ArrayList<>();

    public String getShowContent() {
        this.showContent = Emotions.formatEmotion(this.content, Emotions.Dev.WEB);
        return showContent;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }
}