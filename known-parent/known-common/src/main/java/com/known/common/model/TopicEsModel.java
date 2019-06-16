package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

/**
 * 话题搜索
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-06-08 13:59
 */
@Data
@NoArgsConstructor
@Document(indexName = "known_topic", type = "known")
public class TopicEsModel {

    @Id
    private String topic_id;
    private String title;
    private String summary;
    private String content;
    private String type;
    private String user_id;
    private String user_name;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date create_time;

    public String getTitle() {
        return title != null ? HtmlUtils.htmlUnescape(title) : null;
    }

    public String getSummary() {
        return summary  != null ? HtmlUtils.htmlUnescape(summary) : null;
    }

    public String getContent() {
        return content != null ? HtmlUtils.htmlUnescape(content) : null;
    }

}
