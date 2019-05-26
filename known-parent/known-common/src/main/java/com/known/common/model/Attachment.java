package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.FileTopicTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {
    private String attachmentId;

    private String articleId;

    private String fileName;

    private String fileUrl;

    private Integer downloadMark;

    private Integer downloadCount = 0;

    
    private FileTopicTypeEnum fileTopicType;
    


    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }


    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }


}