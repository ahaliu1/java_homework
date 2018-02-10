package search.impl;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import search.Parser;

import vo.Program;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;


public class ParserImpl implements Parser {
    @Override
    public Program parseHtml(String html) {
        Program program = new Program();
        Document doc = Jsoup.parse(html); //解析HTML字符串返回一个Document实现

        Elements link = doc.getElementsByTag("a");
        String website = link.attr("href");

        String nameAndSchool = link.text();

        //正则表达式提取学院、项目名
        String rex = "[()]+";
        String[] str = nameAndSchool.split(rex);

        str[0]= str[0].replace("'","\\`");
        program.setProgramName(str[0]);
        if (str.length > 1) {
            program.setSchool(str[1]);
        } else {
            program.setSchool("NULL");
        }
        program.setId(UUID.randomUUID().toString().replace("-",""));
        program.setHomepage(website);
        program.setCountry("United States");
        program.setUniversity("Columbia University");
        program.setDeadlineWithAid("NULL");
        program.setDegree("NULL");
        program.setDeadlineWithoutAid("NULL");
        program.setEmail("NULL");
        program.setLocation("NULL");
        program.setPhoneNumber("NULL");

        return program;
    }


}
