package main;

import search.FileHandler;
import search.WebSpider;

import java.util.ArrayList;
import java.util.List;

public final class SearchManager {

    private static List<WebSpider> webSpiders;

    private static FileHandler fileHandler;

    private SearchManager(){}

    public static void registSpider(WebSpider webSpider){
        getWebSpider().add(webSpider);
    }

    public static void registFileHandler(FileHandler fh){
        fileHandler = fh;
    }

    public static List<WebSpider> getWebSpider(){
        if(webSpiders == null){
            synchronized (SearchManager.class){
                if(webSpiders == null)
                    webSpiders = new ArrayList<>();
            }
        }
        return webSpiders;
    }


    public static FileHandler getFileHandler() {
        return fileHandler;
    }
}
