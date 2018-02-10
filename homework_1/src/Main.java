import interfaces.FileHandler;
import interfaces.StockSorter;
import vo.StockInfo;
import vo.StockFileHandler;
import vo.StockSorterClass;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

public class Main {

    private static FileHandler fileHandler;

    private static StockSorter stockSorter;

    static{
        //TODO:Initialize fileHandler and stockSorter with your implement class
            fileHandler=new StockFileHandler();
            stockSorter=new StockSorterClass();

    }

    public static void main(String[] args) throws Exception{
	// write your code here
        System.out.println("输入需处理文件地址");
        Scanner input =new Scanner(System.in);
        String filePath = input.nextLine();
        File test = new File(filePath);
        if (test.exists()) System.out.println("读取文件存在");
        else System.out.println("读取文件不存在");


        String targetPath = input.nextLine();
        File test1 = new File(targetPath);
        if (test1.exists()) System.out.println("输出文件存在");
        else System.out.println("输出文件不存在");

        //数据读取
        StockInfo[] stocks = fileHandler.getStockInfoFromFile(filePath);
        if(stocks != null)
            System.out.println("数据读入成功");

        //数据排序
        //true升序，false 降序
        System.out.println("输入1升序，输入0降序");

        int a = input.nextInt();
        boolean choice=false;
        if(a ==1){
            choice=true;
        }
        StockInfo[] sortedStocks = stockSorter.sort(stocks,choice);
        System.out.println("排序结束");

        int writeLenght = fileHandler.setStockInfo2File(targetPath,sortedStocks);
        Formatter formatter = new Formatter(System.out);
        if(writeLenght == sortedStocks.length)
            formatter.format("写入操作成功，共写入%d条数据",writeLenght);
        else
            formatter.format("写入失败");

    }

}
