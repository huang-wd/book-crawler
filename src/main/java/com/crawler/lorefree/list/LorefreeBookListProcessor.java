package com.crawler.lorefree.list;

import com.crawler.lorefree.entity.LorefreeBookList;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 提取每个url爬取下来的数据
 */
@Slf4j
public class LorefreeBookListProcessor implements PageProcessor {

    public static final String URL_LIST = "https://ebook\\.lorefree\\.com/\\?p=\\d+";

    private LorefreeBookListProducer producer;

    public LorefreeBookListProcessor(LorefreeBookListProducer producer) {
        this.producer = producer;
    }

    private Site site = Site.me()
            .setDomain("ebook.lorefree.com")
            .setTimeOut(10000)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/547.31 (KHTML, like Gecko) Chrome/27.0.1410.65 Safari/547.31");

    @Override
    public void process(Page page) {
        log.info("开始处理页面 {}", page.getUrl().get());

        //往爬虫中加入待爬取的url
        while (!producer.isEmpty()) {
            page.addTargetRequest(producer.take().getSourceUrl());
        }

        //列表页
        if (!page.getUrl().regex(URL_LIST).match()) {
            log.warn("未匹配的url {}", page.getUrl().get());
            page.setSkip(true);
            return;
        }

        //添加每页上的详情信息链接
        List<String> detailList = page.getHtml().xpath("//div[@class='caption book-content']/a").links().all();

        page.putField("detail_url_list", detailList);
        page.putField("source_url", page.getUrl().get());

        //如果待爬取队列为空，则等待生产者最多5s，在此期间生产者如果产生了待爬取的url，则添加到待爬取队列
        if (page.getTargetRequests().isEmpty()) {
            LorefreeBookList lorefreeBookList = producer.poll(5, TimeUnit.SECONDS);
            if (lorefreeBookList != null) {
                page.addTargetRequest(lorefreeBookList.getSourceUrl());
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

}
