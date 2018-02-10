package main;

import search.FileHandler;
import search.Parser;
import search.WebSpider;
import vo.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * 请不要对此接口进行任何修改
 */
public final class Searcher {

    /**
     * 请不要对此函数进行任何操作，一句话也不允许加
     * @param args
     */
    public static void main(String[] args) {

        try {
            Class.forName("search.impl.Manager");
        } catch (ClassNotFoundException e) {
            System.out.println("Manager类不存在");
        }

        List<WebSpider> webSpiders = SearchManager.getWebSpider();

        List<Program> programs = new ArrayList<>();
        for (WebSpider webSpider : webSpiders) {
            Parser parser = webSpider.getParser();
            List<String> pages = webSpider.getHtmlFromWeb();
            for (String page : pages) {
                programs.add(parser.parseHtml(page));
            }
        }

        FileHandler fileHandler = SearchManager.getFileHandler();
        fileHandler.program2File(programs);


    }
}
