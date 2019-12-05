package com.crawler.lorefree.detail;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;

import javax.management.JMException;

/**
 * 启动类
 * 根据详情页url来爬取详情页信息
 */
public class LorefreeBookDetailApp {

    public static void main(String[] args) throws JMException {
        LorefreeBookDetailProducer producer = new LorefreeBookDetailProducer();
        producer.start();

        Spider spider = Spider.create(new LorefreeBookDetailProcessor(producer))
                .addUrl(producer.take().getSourceUrl())
                //.addPipeline(new ConsolePipeline())
                .addPipeline(new LorefreeBookDetailPipeline())
                .thread(30)
                .setExitWhenComplete(true);

        SpiderMonitor.instance().register(spider);
        spider.start();
    }
}
