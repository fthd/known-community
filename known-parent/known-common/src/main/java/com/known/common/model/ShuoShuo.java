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
    private Integer id;

    private Integer userId;

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

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public void setImageUrlSmall(String imageUrlSmall) {
        this.imageUrlSmall = imageUrlSmall == null ? null : imageUrlSmall.trim();
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl == null ? null : musicUrl.trim();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

}