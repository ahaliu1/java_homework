package database;

import vo.Program;

import java.util.List;

public class DataHandler {

    //将每一个program写入数据库
    public void program2DB(List<String> programsContent) {
        DataBase dataBase = new DataBase();

        for (String p : programsContent) {
            dataBase.add2Table(p);
        }
    }
    //调用database的search方法并将返回结果存入一个二维数组返回，供jtable显示使用
    public Object[][] getSearchResult(String choice, String keyWord)throws Exception{

        DataBase dataBase = new DataBase();


        List<Program> list=dataBase.search(choice,keyWord);

        //将program用Jtable需要的二维数组存储
        Object table[][]= new Object[list.size()][12];
        for (int i=0;i<list.size();i++){
            table[i][0]=list.get(i).getId();
            table[i][1]=list.get(i).getCountry();
            table[i][2]=list.get(i).getUniversity();
            table[i][3]=list.get(i).getSchool();
            table[i][4]=list.get(i).getProgramName();
            table[i][5]=list.get(i).getHomepage();
            table[i][6]=list.get(i).getLocation();
            table[i][7]=list.get(i).getEmail();
            table[i][8]=list.get(i).getPhoneNumber();
            table[i][9]=list.get(i).getDegree();
            table[i][10]=list.get(i).getDeadlineWithAid();
            table[i][11]=list.get(i).getDeadlineWithoutAid();
        }
        return table;
    }



}
