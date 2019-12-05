package com.crawler.lorefree.entity.handler;

import com.crawler.lorefree.entity.LorefreeBookList;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

public class LorefreeBookListHandler extends BeanListHandler<LorefreeBookList> {

    public LorefreeBookListHandler() {
        super(LorefreeBookList.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
    }

    public static Map<String, String> getColumnsToFieldsMap() {
        Map<String, String> columnsToFieldsMap = new HashMap<>(4);
        columnsToFieldsMap.put("page_num", "pageNum");
        columnsToFieldsMap.put("source_url", "sourceUrl");
        columnsToFieldsMap.put("create_time", "createTime");
        columnsToFieldsMap.put("update_time", "updateTime");
        return columnsToFieldsMap;
    }
}
