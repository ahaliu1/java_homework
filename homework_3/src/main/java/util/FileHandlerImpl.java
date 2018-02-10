package util;

import vo.StockInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class FileHandlerImpl implements FileHandler {
    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     *
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    @Override
    public StockInfo[] getStockInfoFromFile(String filePath)
            throws Exception {
        // -1目的是跳过表头
        File file = new File(filePath);
        int length = length(file) - 1;
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),
                "UTF-8");
        BufferedReader input = new BufferedReader(isr);
        String[] Data = new String[length];
        StockInfo[] info = new StockInfo[length];
        // 跳过表头
        input.readLine();

        for (int i = 0; i < length; i++) {
            info[i] = new StockInfo();
            Data[i] = input.readLine();

            String[] ALineOfDate = Data[i].split("\t");
            info[i].setId(ALineOfDate[0]);
            info[i].setTitle(ALineOfDate[1]);
            info[i].setAuthor(ALineOfDate[2]);
            info[i].setDate(ALineOfDate[3]);
            info[i].setLastupdate(ALineOfDate[4]);
            info[i].setContent(ALineOfDate[5]);
            info[i].setAnswerauthor(ALineOfDate[6]);
            info[i].setAnswer(ALineOfDate[7]);
        }

        input.close();

        return info;
    }

    /**
     * 这个函数用来计算文本文件的行数
     * @param inputfile
     * @return
     * @throws Exception
     */
    public int length(File inputfile) throws Exception {
        FileInputStream inputstream = new FileInputStream(inputfile);
        BufferedReader file = new BufferedReader(new InputStreamReader(
                (inputstream)));
        int length = 0;

        while (file.readLine() != null) {
            length++;
        }

        return length;
    }
}
