package search;

import vo.Program;

import java.util.ArrayList;
import java.util.List;

public final class SpiderStarter {

    private static List<Program> programs=null;

    public List<Program> getPrograms() {
        return programs;
    }

    public List<String> getPragramsContent(List<Program> programs) {
        List<String> programsContent =new ArrayList<>();
        for (Program p : programs) {
            programsContent.add(p.getId() + "\t" + p.getCountry() + "\t" + p.getUniversity() + "\t" + p.getSchool()
                    + "\t" + p.getProgramName() + "\t" + p.getHomepage() + "\t" + p.getLocation() + "\t" +
                    p.getEmail() + "\t" + p.getPhoneNumber() + "\t" + p.getDegree() + "\t" +
                    p.getDeadlineWithAid() + "\t" + p.getDeadlineWithoutAid());
        }
        return programsContent;
    }

    public void spider(){

        try {
            Class.forName("search.impl.Manager");
        } catch (ClassNotFoundException e) {
            System.out.println("Manager类不存在");
        }

        List<WebSpider> webSpiders = SpiderManager.getWebSpider();

         programs = new ArrayList<>();
        for (WebSpider webSpider : webSpiders) {
            Parser parser = webSpider.getParser();
            List<String> pages = webSpider.getHtmlFromWeb();
            for (String page : pages) {
                programs.add(parser.parseHtml(page));
            }
        }



    }
}
