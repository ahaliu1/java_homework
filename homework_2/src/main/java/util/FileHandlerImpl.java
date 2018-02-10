package util;

import vo.StockInfo;
import vo.UserInterest;

import java.io.*;
import java.util.Scanner;

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
    public StockInfo[] getStockInfoFromFile(String filePath) throws Exception{
        File file = new File(filePath);
        // -1目的是跳过表头
        int length = length(file)-1;
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader input = new BufferedReader(isr);
        String[] Data = new String[length];
        StockInfo[] info = new StockInfo[length];
        // 跳过表头
        input.readLine();
        for(int i=0;i<length;i++){
            info[i]=new StockInfo();
            Data[i] = input.readLine();
            String[] ALineOfDate = Data[i].split("\t");
            info[i].ID = ALineOfDate[0];
            info[i].TITLE = ALineOfDate[1];
            info[i].AUTHOR = ALineOfDate[2];
            info[i].DATE = ALineOfDate[3];
            info[i].LASTUPDATE = ALineOfDate[4];
            info[i].CONTENT = ALineOfDate[5];
            info[i].ANSWERAUTHOR = ALineOfDate[6];
            info[i].ANSWER = ALineOfDate[7];
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
        BufferedReader file = new BufferedReader(new InputStreamReader((inputstream)));
        int length = 0;
        while (file.readLine() != null) {
            length++;
        }
        return length;

    }

    /**
     * This func gets user interesting from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     *
     * @param filePath
     * @return
     */
    @Override
    public UserInterest[] getUserInterestFromFile(String filePath)throws Exception{

        File file = new File(filePath);
        // 获得文件行数
        int length = length(file);
        //
        UserInterest[] interestMatrix= new UserInterest[length];
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        BufferedReader input = new BufferedReader(isr);
        for(int i=0;i<length;i++){
            String temp= input.readLine();
            interestMatrix[i]=new UserInterest();
           for (int j=0;j<60;j++){
               // 因为charAt读0为48 读1为49 所以减去48
               interestMatrix[i].userInterest[j]=temp.charAt(j)-48;
           }

        }
        input.close();
        return interestMatrix;
    }

    /**
     * This function need write matrix to files
     *
     * @param matrix the matrix you calculate
     */
    @Override
    public void setCloseMatrix2File(double[][] matrix) throws Exception{
        File file =new File(this.getClass().getClassLoader().getResource(".")
                .getPath() + "CloseMatrix.txt");
        PrintWriter output = new PrintWriter(file);
        for(int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[i].length;j++){
                output.print(matrix[i][j]+"\t");
            }
            output.print("\n");
        }
        output.close();
    }

    /**
     * This function need write recommend to files
     *
     * @param recommend the recommend you calculate
     */
    @Override
    public void setRecommend2File(double[][] recommend)throws Exception {
        File file =new File(this.getClass().getClassLoader().getResource(".")
                .getPath() + "RecommendMatrix.txt");
        PrintWriter output = new PrintWriter(file);
        for(int i=0;i<recommend.length;i++){
            for (int j=0;j<recommend[i].length;j++){
                output.print((int)recommend[i][j]+"\t");
            }
            output.print("\n");
        }
        output.close();
    }
}
