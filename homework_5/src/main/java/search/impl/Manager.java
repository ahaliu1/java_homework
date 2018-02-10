package search.impl;

import search.SpiderManager;

/**
 * 请在此类中完成自己对象初始化工作，并注册
 */
public class Manager {

    static{
        // TODO:在此初始化所需组件，并将其注册到SearchManager中供主函数使用
        // SpiderManager.registFileHandler(new yourFileHandler());
        // SpiderManager.registSpider(new yourSpider());
        SpiderManager.registSpider(new WebSpiderImpl());
    }
}
