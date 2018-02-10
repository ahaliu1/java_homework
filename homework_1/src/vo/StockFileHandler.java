package vo;

import java.io.*;
import java.util.Scanner;

public class StockFileHandler implements interfaces.FileHandler {
    public StockInfo[] getStockInfoFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        int length = length(file);
        Scanner input = new Scanner(file);
        int i = 0;
        String[] Data = new String[length];
        StockInfo[] info = new StockInfo[length];

        while (i < length) {
            Data[i] = input.nextLine();
            String[] ALineOfDate = Data[i].split("\t");//
            info[i] = new StockInfo();
            info[i].ID = ALineOfDate[0];
            info[i].TITLE = ALineOfDate[1];
            info[i].AUTHOR = ALineOfDate[2];
            info[i].DATE = ALineOfDate[3];
            info[i].LASTUPDATE = ALineOfDate[4];
            info[i].CONTENT = ALineOfDate[5];
            info[i].ANSWERAUTHOR = ALineOfDate[6];
            info[i].ANSWER = ALineOfDate[7];
            i++;
        }
        input.close();
        return info;
    }

    public int setStockInfo2File(String filePath, StockInfo[] stocks) throws Exception {
        java.io.File newfile = new java.io.File(filePath);
        java.io.PrintWriter output = new java.io.PrintWriter(newfile);
        int returnvalue = 0;
        for (StockInfo e : stocks) {
            output.print(e.ID + "   ");
            output.print(e.TITLE + "    ");
            output.print(e.AUTHOR + "   ");
            output.print(e.DATE + " ");
            output.print(e.LASTUPDATE + "   ");
            output.print(e.CONTENT + "  ");
            output.print(e.ANSWERAUTHOR + " ");
            output.println(e.ANSWER);
            returnvalue++;
        }
        output.close();

        return returnvalue;
    }

    public int length(File inputfile) throws Exception {
        FileInputStream inputstream = new FileInputStream(inputfile);
        BufferedReader file = new BufferedReader(new InputStreamReader((inputstream)));
        int length = 0;
        while (file.readLine() != null) {
            length++;
        }
        return length;

    }
}


