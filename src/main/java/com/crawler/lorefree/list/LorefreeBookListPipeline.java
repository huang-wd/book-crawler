package com.crawler.lorefree.list;

import com.crawler.lorefree.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 保存从网页提取出来的数据
 */
@Slf4j
public class LorefreeBookListPipeline implements Pipeline {

    /**
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> detailUrlList = resultItems.get("detail_url_list");
        String sourceUrl = resultItems.get("source_url");

        //更新列表页url记录为已爬取
        DBUtil.update("update lorefree_book_list set status=1 where source_url=?", sourceUrl);

        /**
         * 初始化详情页url，状态为未爬取
         */
        StringBuffer createSql = new StringBuffer();
        createSql.append("insert into lorefree_book_detail(book_id,source_url) values (?, ?)");

        Object[][] params = new Object[detailUrlList.size()][2];

        for (int i = 0; i < detailUrlList.size(); i++) {
            String detailUrl = detailUrlList.get(i);
            List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(detailUrl, Charset.forName("utf-8"));
            int bookId = 0;
            for (NameValuePair nameValuePair : nameValuePairs) {
                if (nameValuePair.getName().equalsIgnoreCase("bookid")) {
                    bookId = NumberUtils.createInteger(nameValuePair.getValue());
                }
            }
            params[i][0] = bookId;
            params[i][1] = detailUrl;
        }

        DBUtil.batch(createSql.toString(), params);
    }

}
