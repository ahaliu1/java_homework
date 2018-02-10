package search.impl;

import search.FileHandler;
import vo.Program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class FileHandlerImpl implements FileHandler {
    @Override
    public int program2File(List<Program> programList) {
        File file =new File("columbia university.txt" );
        try {
            PrintWriter output = new PrintWriter(file);
            for (Program p:programList) {

                output.print(p.getUniversity()+"\t");
                output.print(p.getCountry()+"\t");
                output.print(p.getProgramName()+"\t");
                output.print(p.getSchool()+"\t");
                output.print(p.getDegree()+"\t");
                output.print(p.getEmail()+"\t");
                output.print(p.getPhoneNumber()+"\t");
                output.print(p.getLocation()+"\t");
                output.print(p.getDeadlineWithAid()+"\t");
                output.print(p.getDeadlineWithoutAid()+"\t");
                output.print(p.getHomepage()+"\n");
            }
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return programList.size();
    }

}
