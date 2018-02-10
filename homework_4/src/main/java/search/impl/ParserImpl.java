package search.impl;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import search.Parser;

import vo.Program;

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

        String rex = "[()]+";
        String[] str = nameAndSchool.split(rex);

        program.setId(UUID.randomUUID().toString().replace("-",""));
        program.setProgramName(str[0]);

        if (str.length > 1) {
            program.setSchool(str[1]);
        } else {
            program.setSchool("NULL");
        }

        program.setHomepage(website);
        program.setCountry("United States");
        program.setUniversity("columbia university");
        program.setDeadlineWithAid("NULL");
        program.setDegree("NULL");
        program.setDeadlineWithoutAid("NULL");
        program.setEmail("NULL");
        program.setLocation("NULL");
        program.setPhoneNumber("NULL");
        program.setId("NULL");

        return program;
    }


}
