package com.crawler;

import com.crawler.lorefree.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LorefreePageUrlInit {

    @Test
    public void pageListUrlInit() {
        String sql = "INSERT INTO lorefree_book_list (`page_num`, `source_url`) VALUE (?,?)";
        for (int i = 1; i <= 4427; i++) {
            DBUtil.insert(sql, i, "https://ebook.lorefree.com/?p=" + i);
        }
    }
}
