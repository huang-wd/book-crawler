package com.crawler.lorefree.detail;

import com.crawler.lorefree.entity.LorefreeBookDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.TimeUnit;

/**
 * 提取每个url爬取下来的数据
 */
@Slf4j
public class LorefreeBookDetailProcessor implements PageProcessor {

    public static final String DETAIL_URL = "https://ebook\\.lorefree\\.com/book\\?account=&bookid=\\w+";

    private LorefreeBookDetailProducer producer;

    public LorefreeBookDetailProcessor(LorefreeBookDetailProducer producer) {
        this.producer = producer;
    }

    private Site site = Site.me()
            .setDomain("ebook.lorefree.com")
            .setTimeOut(10000)
            .setRetryTimes(3)
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/547.31 (KHTML, like Gecko) Chrome/27.0.1410.65 Safari/547.31");

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        log.info("开始处理页面 {}", page.getUrl().get());

        //往爬虫中加入待爬取的url
        if (!producer.isEmpty()) {
            LorefreeBookDetail nextLorefreeBookDetail = producer.poll(10, TimeUnit.MILLISECONDS);
            if (nextLorefreeBookDetail != null) {
                page.addTargetRequest(nextLorefreeBookDetail.getSourceUrl());
            }
        }

        //详情页
        if (!page.getUrl().regex(DETAIL_URL).match()) {
            log.warn("未匹配的url {}", page.getUrl().get());
            page.setSkip(true);
            return;
        }

        //文章页
        LorefreeBookDetail lorefreeBookDetail = new LorefreeBookDetail();

        lorefreeBookDetail.setTitle(page.getHtml().xpath("//div[@class='row  book-cont']/h4/text()").get());
        lorefreeBookDetail.setSubTitle(page.getHtml().xpath("//div[@class='row  book-cont']/h5/text()").get());
        Float score = NumberUtils.createFloat(page.getHtml().xpath("//div[@class='row  book-cont']//span[@class='label label-primary']/text()").get());

        if (score != null) {
            score = score * 10;
            lorefreeBookDetail.setScore(score.intValue());
        }

        Integer collectionCount = NumberUtils.createInteger(page.getHtml().xpath("//div[@class='row  book-cont']//span[@class='collection_count']/text()").get());
        lorefreeBookDetail.setCollectionCount(collectionCount);
        lorefreeBookDetail.setSourceUrl(page.getUrl().get());

        page.putField("book_info", lorefreeBookDetail);

        //如果待爬取队列为空，则等待生产者最多5s，在此期间生产者如果产生了待爬取的url，则添加到待爬取队列
        if (page.getTargetRequests().size() <= 1) {
            LorefreeBookDetail nextLorefreeBookDetail = producer.poll(5, TimeUnit.SECONDS);
            if (nextLorefreeBookDetail != null) {
                page.addTargetRequest(nextLorefreeBookDetail.getSourceUrl());
            }
        }
    }


}
