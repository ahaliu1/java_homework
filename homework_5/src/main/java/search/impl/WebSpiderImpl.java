package search.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import search.Parser;
import search.WebSpider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebSpiderImpl implements WebSpider {
    @Override
    public Parser getParser() {
        Parser parser = new ParserImpl();
        return parser;
    }

    @Override
    public List<String> getHtmlFromWeb() {
        //获得主页的html
        URL url = null;
        String homePageHtml = null;
        try {
            url = new URL("http://www.columbia.edu/node/1846.html");
            InputStream in = url.openStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader bufr = new BufferedReader(isr);
            homePageHtml = new String();
            String a;
            while ((a = bufr.readLine()) != null) {
                homePageHtml += a;
            }
            bufr.close();
            isr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(homePageHtml);//解析HTML字符串返回一个Document实现

        List<String> htmls = new ArrayList<>();

        for(int i = 1;i<=23;i++)//遍历23个字母
        {
            int j =1;
            do{
                Elements elements = doc.select("#main-content-inner-inner > div.region.region-content-bottom.clearfix > " +
                        "div.view.view-directory-listing.view-id-directory_listing.view-display-id-block_1.view-dom-id-1 > div > " +
                        "div:nth-child("+i+") > ul > li.views-row.views-row-"+j+" > div > a");
                if(elements.toString().equals(""))
                {
                    break;
                }
                htmls.add(elements.toString());
                j++;
            }while (true);
        }

        return htmls;
    }
}
