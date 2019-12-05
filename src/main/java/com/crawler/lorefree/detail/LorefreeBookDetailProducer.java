package com.crawler.lorefree.detail;

import com.crawler.lorefree.entity.LorefreeBookDetail;
import com.crawler.lorefree.entity.handler.LorefreeBookDetailHandler;
import com.crawler.lorefree.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 从数据库加载要爬取的详情页url，用来爬取详情信息
 */
@Slf4j
public class LorefreeBookDetailProducer extends Thread {

    private LinkedBlockingQueue<LorefreeBookDetail> queue = new LinkedBlockingQueue<>();

    public LorefreeBookDetailProducer() {
        super("detail-producer");
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
                    .append(" limit 60");

            List<LorefreeBookDetail> result = DBUtil.findByCondition("lorefree_book_detail", condition.toString(), new LorefreeBookDetailHandler());

            if (CollectionUtils.isNotEmpty(result)) {
                queue.addAll(result);
                id = result.get(result.size() - 1).getId();
            } else {
                break;
            }

            while (queue.size() > 1000) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error("", e);
                }
            }
        }

        log.info("生产者生产完成!!!!!!!!!");
    }

    public LorefreeBookDetail take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return null;
    }

    public LorefreeBookDetail poll(long timeout, TimeUnit unit) {
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
