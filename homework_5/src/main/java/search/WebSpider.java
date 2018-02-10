package search;

import java.util.List;

public interface WebSpider {

    /**
     * 获得此爬虫解析器
     * @return
     */
    Parser getParser();

    /**
     * 从页面中爬取内容
     * @return
     */
    List<String> getHtmlFromWeb();

}
