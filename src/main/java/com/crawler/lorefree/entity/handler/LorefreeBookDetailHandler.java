package com.crawler.lorefree.entity.handler;

import com.crawler.lorefree.entity.LorefreeBookDetail;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

public class LorefreeBookDetailHandler extends BeanListHandler<LorefreeBookDetail> {

    public LorefreeBookDetailHandler() {
        super(LorefreeBookDetail.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
    }

    public static Map<String, String> getColumnsToFieldsMap() {
        Map<String, String> columnsToFieldsMap = new HashMap<>(4);
        columnsToFieldsMap.put("book_id", "bookId");
        columnsToFieldsMap.put("sub_title", "subTitle");
        columnsToFieldsMap.put("collection_count", "collectionCount");
        columnsToFieldsMap.put("source_url", "sourceUrl");
        columnsToFieldsMap.put("create_time", "createTime");
        columnsToFieldsMap.put("update_time", "updateTime");
        return columnsToFieldsMap;
    }
}
