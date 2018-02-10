package search;

import java.util.ArrayList;
import java.util.List;

public final class SpiderManager {

    private static List<WebSpider> webSpiders;

    private SpiderManager(){}

    public static void registSpider(WebSpider webSpider){
        getWebSpider().add(webSpider);
    }

    public static List<WebSpider> getWebSpider(){
        if(webSpiders == null){
            synchronized (SpiderManager.class){
                if(webSpiders == null)
                    webSpiders = new ArrayList<>();
            }
        }
        return webSpiders;
    }


}
