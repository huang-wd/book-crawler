package com.crawler.lorefree.entity;

import lombok.Data;

@Data
public class LorefreeBookList extends BaseEntity {
    private Integer pageNum;
    private Integer status;
    private String sourceUrl;
}