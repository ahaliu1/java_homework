package database;


import com.mysql.jdbc.NotUpdatable;
import com.mysql.jdbc.log.NullLogger;
import vo.Program;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBase {

    private static Connection connection = null;
    private static Statement statement = null;
    private static String url;
    private static String username;
    private static String password;

    //连接本地数据库
    public void connectLocalDB() {

        setUrl("jdbc:mysql://localhost:3306/javahomework_db");
        setUsername("root");
        setPassword("lty7545981012");
        try {
            if (statement != null) {
                statement.close();
                connection.close();
            }
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //连接云数据库
    public void connectCloudDB() {
        setUrl("jdbc:mysql://119.27.166.115:2017/java_exp");
        setUsername("whu_iss_2017");
        setPassword("iss_java_2017");
        //获取数据库连接
        try {
            if (statement != null) {
                statement.close();
                connection.close();
            }
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void add2Table(String data) {
        String[] a = data.split("\t");

        //插入的sql语句
        String sql = "INSERT INTO program(Id, country, university, " +
                "school, program_name, homepage, location, email, phone_number, " +
                "degree, deadline_with_aid, deadline_without_aid) VALUES(" + "'" + a[0] + "'," + "'" + a[1] + "'," + "'" + a[2] + "',"
                + "'" + a[3] + "'," + "'" + a[4] + "'," + "'" + a[5] + "'," + "'" + a[6] + "'," + "'" + a[7] + "'," +
                "'" + a[8] + "'," + "'" + a[9] + "'," + "'" + a[10] + "'," + "'" + a[11] + "')";

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //搜索当前数据库
    public List<Program> search(String choice, String keyWord) throws Exception {
        List<Program> list = new ArrayList<>();
        ResultSet resultSet = null;

        //如果用户在界面选择的搜索字段为all则搜索全部列，如果选择具体的字段则选择对应的列搜索
        if (choice.equals("all")) {
            String sql = "SELECT DISTINCT * FROM program  WHERE id LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE country LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE university LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE school LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE program_name LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE homepage LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE location LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE email LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE phone_number LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE degree LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE deadline_with_aid LIKE '%" + keyWord + "%'"
                    + "UNION " + "SELECT DISTINCT * FROM program  WHERE deadline_without_aid LIKE '%" + keyWord + "%'";

            resultSet = statement.executeQuery(sql);
        } else {
            //在相应的列中精确查找
            String sql = "SELECT DISTINCT * FROM program  WHERE " + choice + "=" + "'" + keyWord + "'";

            resultSet = statement.executeQuery(sql);

            //如果不能完全匹配则模糊查找
            if (resultSet.next() == false) {
                sql = "SELECT DISTINCT * FROM program  WHERE " + choice + "  LIKE " +"'%"+keyWord+"%'";
                resultSet = statement.executeQuery(sql);
            } else {
                resultSet.beforeFirst();//返回第一行之前
            }
        }

        //将查询结果创建program对象并用html标签标红
        while (resultSet.next()) {
            Program program = new Program();
            //为了防止某些空字段，所以要try catch
            try {
                program.setId("<html>" + resultSet.getString(1).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setId("NULL");
            }
            try {
                program.setCountry("<html>" + resultSet.getString(2).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setCountry("NULL");
            }
            try {
                program.setUniversity("<html>" + resultSet.getString(3).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setUniversity("NULL");
            }
            try {
                program.setSchool("<html>" + resultSet.getString(4).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setSchool("NULL");
            }
            try {
                program.setProgramName("<html>" + resultSet.getString(5).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setProgramName("NULL");
            }
            try {
                program.setHomepage("<html>" + resultSet.getString(6).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch(NullPointerException e){
                program.setHomepage("NULL");
            }
            try {
                program.setLocation("<html>" + resultSet.getString(7).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch(NullPointerException e){
                program.setLocation("NULL");
            }
            try {
                program.setEmail("<html>" + resultSet.getString(8).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));

            } catch (NullPointerException e) {
                program.setEmail("NULL");
            }
            try {
                program.setPhoneNumber("<html>" + resultSet.getString(9).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setPhoneNumber("NULL");
            }
            try {
                program.setDegree("<html>" + resultSet.getString(10).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setDegree("NULL");
            }
            try {
                program.setDeadlineWithAid("<html>" + resultSet.getString(11).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setDeadlineWithAid("NULL");
            }
            try {
                program.setDeadlineWithoutAid("<html>" + resultSet.getString(12).replace(keyWord, "<span><font color=red>" + keyWord + "</font></span>"));
            }catch (NullPointerException e){
                program.setDeadlineWithoutAid("NULL");
            }
            list.add(program);

        }
        return list;
    }

    //建表
    public void createTable() {
        try {
            String createTable = "CREATE TABLE `program` (\n" +
                    "  `Id` varchar(32) NOT NULL DEFAULT '' COMMENT 'UUID 随机32位字母数字',\n" +
                    "  `country` varchar(255) NOT NULL DEFAULT '' COMMENT ' 国家',\n" +
                    "  `university` varchar(255) NOT NULL DEFAULT '' COMMENT ' 学校',\n" +
                    "  `school` varchar(255) NOT NULL DEFAULT '' COMMENT ' 学校',\n" +
                    "  `program_name` varchar(255) NOT NULL DEFAULT '' COMMENT ' 项目名称',\n" +
                    "  `homepage` varchar(255) NOT NULL DEFAULT '' COMMENT ' 项目主页',\n" +
                    "  `location` varchar(255) DEFAULT NULL COMMENT ' 项目地址',\n" +
                    "  `email` varchar(255) DEFAULT NULL COMMENT ' 申请邮箱',\n" +
                    "  `phone_number` varchar(255) DEFAULT NULL COMMENT ' 联系方式',\n" +
                    "  `degree` varchar(255) NOT NULL DEFAULT '' COMMENT ' 学位',\n" +
                    "  `deadline_with_aid` varchar(255) DEFAULT NULL COMMENT ' 申请截止时间（奖学金）',\n" +
                    "  `deadline_without_aid` varchar(255) DEFAULT NULL COMMENT ' 申请截止时间（无奖学金）'\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=' 项目表';\n" +
                    "\n";
            statement.execute(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //添加
    public void insert(Program program){

        String sql = "INSERT INTO program(Id, country, university, " +
                "school, program_name, homepage, location, email, phone_number, " +
                "degree, deadline_with_aid, deadline_without_aid) VALUES("+"'"+program.getId()+"',"+"'"+program.getCountry()+"',"+"'"+program.getUniversity()+"',"+"'"+program.getSchool()+"',"
                +"'"+program.getProgramName()+"',"+"'"+program.getHomepage()+"',"+"'"+program.getLocation()+"',"+"'"+program.getEmail()+"',"+"'"+program.getPhoneNumber()+"',"+
                "'"+program.getDegree()+"',"+"'"+program.getDeadlineWithAid()+"',"+"'"+program.getDeadlineWithoutAid()+"')";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除
    public void delete(String id){
        String sql ="DELETE FROM program WHERE id ="+"'"+id+"'";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更改
    public void update(Program program){
        String sql=null;
        try {
            //更改语句
            sql ="UPDATE program SET id ="+"'"+program.getId()+"',"+
                    "country ="+"'"+program.getCountry()+"',"+
                    "university ="+"'"+program.getUniversity()+"',"+
                    "school =" +"'"+program.getSchool()+"',"+
                    "program_name =" +"'"+program.getProgramName()+"',"+
                    "homepage =" +"'"+program.getHomepage()+"',"+
                    "location ="+"'"+program.getLocation()+"',"+
                    "email =" +"'"+program.getEmail()+"',"+
                    "phone_number ="+"'"+program.getPhoneNumber()+"',"+
                    "degree ="+"'"+program.getDegree()+"',"+
                    "deadline_with_aid ="+"'"+program.getDeadlineWithAid()+"',"+
                    "deadline_without_aid ="+"'"+program.getDeadlineWithoutAid()+"'"+"WHERE id ="+"'"+program.getId()+"'";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
