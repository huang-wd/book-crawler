package com.crawler.lorefree.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class LorefreeBook {
    private int id;
    private String title;
    private String shortDescription;
    private Integer score;
    private Integer collectionCount;
    private String sourceUrl;

    public String getTitle() {
        if (StringUtils.isBlank(title)) {
            return "";
        }
        return title;
    }

    public String getShortDescription() {
        if (StringUtils.isBlank(shortDescription)) {
            return "";
        }
        return shortDescription;
    }
}
