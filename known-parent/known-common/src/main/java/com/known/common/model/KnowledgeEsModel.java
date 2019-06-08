package com.known.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 知识库搜索
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-06-08 13:59
 */
@Data
@NoArgsConstructor
@Document(indexName = "known_knowledge", type = "known")
public class KnowledgeEsModel {

    private String knowledgeId;
    private String title;
    private String summary;
    private String content;
    private String type;
    private String createTime;

}
