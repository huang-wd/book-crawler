package com.crawler.lorefree.list;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

import javax.management.JMException;

/**
 * 启动类
 * 根据列表页url来爬取详情页url
 */
public class LorefreeBookListApp {

    public static void main(String[] args) throws JMException {
        LorefreeBookListProducer producer = new LorefreeBookListProducer();
        producer.start();

        Spider spider = Spider.create(new LorefreeBookListProcessor(producer))
                .addUrl(producer.take().getSourceUrl())
                //.addPipeline(new ConsolePipeline())
                .addPipeline(new LorefreeBookListPipeline())
                .thread(30)
                .setExitWhenComplete(true);

        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
