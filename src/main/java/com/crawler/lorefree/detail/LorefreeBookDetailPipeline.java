package com.crawler.lorefree.detail;

import com.crawler.lorefree.entity.LorefreeBookDetail;
import com.crawler.lorefree.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 保存从网页提取出来的数据
 */
@Slf4j
public class LorefreeBookDetailPipeline implements Pipeline {

    /**
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        LorefreeBookDetail lorefreeBookDetail = resultItems.get("book_info");
        DBUtil.update("update lorefree_book_detail set `status`=?, `title`=?, `sub_title`=?, `score`=?, `collection_count`=? where source_url=?",
                1,
                lorefreeBookDetail.getTitle(),
                lorefreeBookDetail.getSubTitle(),
                lorefreeBookDetail.getScore(),
                lorefreeBookDetail.getCollectionCount(),
                lorefreeBookDetail.getSourceUrl());
    }

}
