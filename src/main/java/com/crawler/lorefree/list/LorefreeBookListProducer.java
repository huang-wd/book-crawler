package com.crawler.lorefree.list;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.crawler.lorefree.util.DBUtil;
import com.crawler.lorefree.entity.LorefreeBookList;
import com.crawler.lorefree.entity.handler.LorefreeBookListHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

/**
 * 从数据库加载要爬取的列表url，用来从爬取的列表页面中提取详情页url
 */
@Slf4j
public class LorefreeBookListProducer extends Thread {

    private LinkedBlockingQueue<LorefreeBookList> queue = new LinkedBlockingQueue<>();

    public LorefreeBookListProducer() {
        super("list-producer");
    }

    @Override
    public void run() {
        int id = 0;
        while (true) {
            StringBuffer condition = new StringBuffer();

            condition.append("status=0")
                    .append(" and ")
                    .append("id>" + id)
                    .append(" order by id asc")
                    .append(" limit 1");

            List<LorefreeBookList> result = DBUtil.findByCondition("lorefree_book_list", condition.toString(), new LorefreeBookListHandler());

            if (CollectionUtils.isNotEmpty(result)) {
                queue.addAll(result);
                id = result.get(result.size() - 1).getId();
            } else {
                break;
            }

        }
    }

    public LorefreeBookList take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return null;
    }

    public LorefreeBookList poll(long timeout, TimeUnit unit) {
        try {
            return queue.poll(timeout, unit);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return null;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
