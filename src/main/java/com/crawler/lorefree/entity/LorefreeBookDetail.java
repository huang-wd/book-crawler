package com.crawler.lorefree.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class LorefreeBookDetail extends BaseEntity {
    private Integer bookId;
    private String title;
    private String subTitle;
    private Integer score;
    private Integer collectionCount;
    private String sourceUrl;

    public String getSubTitle() {
        if (StringUtils.isBlank(subTitle)) {
            return "";
        }
        return subTitle;
    }

    public Integer getScore() {
        if (score == null) {
            return 0;
        }
        return score;
    }

    public Integer getCollectionCount() {
        if (collectionCount == null) {
            return 0;
        }
        return collectionCount;
    }
}