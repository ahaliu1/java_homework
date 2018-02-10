package frame;

import database.DataHandler;
import database.DataBase;
import search.SpiderStarter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainFrame extends JFrame {

    private final JButton openCloudDB;
    private final JButton openLocalDB;
    private final JButton searchButton;
    private final JButton spiderButton;
    private final JButton write2DBButton;
    private final JButton insertButton;
    private final JTextField text;
    private final JTable jTable;
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel labe3;
    private final JComboBox jComboBox;

    String[] columnNames ={"Id","country","university","school","program_name","homepage","location","email","phone_number","degree","ddl_with_aid","ddl_without_add"};


    public MainFrame() {

        super("大学信息搜索");
        setLayout(null);
        //添加控件
        jComboBox=new JComboBox();
        jComboBox.setBounds(20,20,150,40);
        jComboBox.addItem("all");
        jComboBox.addItem("id");
        jComboBox.addItem("country");
        jComboBox.addItem("university");
        jComboBox.addItem("school");
        jComboBox.addItem("program_name");
        jComboBox.addItem("homepage");
        jComboBox.addItem("location");
        jComboBox.addItem("email");
        jComboBox.addItem("phone_number");
        jComboBox.addItem("degree");
        jComboBox.addItem("deadline_with_aid");
        jComboBox.addItem("deadline_without_aid");
        add(jComboBox);

        text = new JTextField(30);
        text.setBounds(200, 20, 600, 40);
        add(text);


        Object row[][]= new Object[30][12];
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setDataVector(row,columnNames);
        jTable = new JTable(tableModel);
        jTable.setBounds(200, 20, 600, 40);
        add(jTable);

        searchButton = new JButton("搜索");
        searchButton.setBounds(850, 20, 130, 40);
        add(searchButton);

        openCloudDB = new JButton("连接云数据库");
        openCloudDB.setBounds(850, 90, 130, 40);
        add(openCloudDB);

        openLocalDB= new JButton("连接本地数据库");
        openLocalDB.setBounds(850, 160, 130, 40);
        add(openLocalDB);

        spiderButton = new JButton("开启爬虫");
        spiderButton.setBounds(850, 230, 130, 40);
        add(spiderButton);

        write2DBButton= new JButton("写入数据库");
        write2DBButton.setBounds(850, 300, 130, 40);
        write2DBButton.setEnabled(false);
        add(write2DBButton);

        insertButton= new JButton("添加");
        insertButton.setBounds(850, 370, 130, 40);
        add(insertButton);

        // 滚动条 PS：修改Jtable大小时需要将这个一同修改
        JScrollPane jsp = new JScrollPane(jTable);
        jsp.setBounds(20, 90, 780, 300);
        add(jsp);

        label1 = new JLabel();
        label1.setBounds(40, 400, 200, 60);
        add(label1);

        label2=new JLabel("未连接数据库");
        label2.setBounds(300,400,200,60);
        add(label2);

        labe3=new JLabel("当前连接数据库:");
        labe3.setBounds(200,400,100,60);
        add(labe3);

        //创建按钮监听器并注册
        ButtonHandler handler = new ButtonHandler();
        searchButton.addActionListener(handler);
        openCloudDB.addActionListener(handler);
        spiderButton.addActionListener(handler);
        write2DBButton.addActionListener(handler);
        openLocalDB.addActionListener(handler);
        insertButton.addActionListener(handler);

        //鼠标监听并注册
        MouseListener listSelectionListener = new MouseListener();
        jTable.addMouseListener(listSelectionListener);
    }

    private class ButtonHandler implements ActionListener {
        private SpiderStarter spiderStarter;
        private DataHandler dataHandler;

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==spiderButton){
                label1.setText("正在爬取数据");
                onPressSpiderButton();
                label1.setText("爬取结束");
                write2DBButton.setEnabled(true);

            }

            if (e.getSource()==write2DBButton){
                label1.setText("正在写入当前数据库");
                dataHandler = new DataHandler();
                dataHandler.program2DB(spiderStarter.getPragramsContent(spiderStarter.getPrograms()));
                label1.setText("写入完成");
            }

            if (e.getSource()==openCloudDB){
                label1.setText("正在连接云数据库");
                DataBase dataBase= new DataBase();
                dataBase.connectCloudDB();
                label1.setText("云数据库连接完成");
                label2.setText("云数据库");
            }

            if(e.getSource()==searchButton){
                onPressSearchButton();
            }

            if(e.getSource()==openLocalDB){
                label1.setText("正在连接本地数据库");
                DataBase dataBase= new DataBase();
                dataBase.connectLocalDB();
                label1.setText("本地数据库连接完成");
                label2.setText("本地数据库");
            }

            if (e.getSource()==insertButton){

                AddFrame addFrame = new AddFrame();
                addFrame.setDefaultCloseOperation(addFrame.getDefaultCloseOperation());
                addFrame.setSize(710, 500);
                addFrame.setVisible(true);
            }
        }

        private void onPressSpiderButton(){
            spiderStarter=new SpiderStarter();
            spiderStarter.spider();
            List<String> programsContent=spiderStarter.getPragramsContent(spiderStarter.getPrograms());
            int rows =programsContent.size();

            //将爬取的信息存入二维数组用于jtable显示
            Object row[][]= new Object[rows][12];
            for(int i=0;i<rows;i++){
                String[] a = programsContent.get(i).split("\t");
                for (int j=0;j<12;j++){
                    row[i][j]=a[j];
                }
            }

            //列名
            String[] columnNames ={"Id","country","university","school","program_name","homepage","location","email","phone_number","degree","ddl_with_aid","ddl_without_add"};

            DefaultTableModel defaultTableModel= new DefaultTableModel();
            defaultTableModel.setDataVector(row,columnNames);
            jTable.setModel(defaultTableModel);}

        private void onPressSearchButton(){
            Object choice = jComboBox.getSelectedItem();

            DataHandler dataHandler =new DataHandler();

            try {
                //搜索结果jtable显示
                Object[][] table = dataHandler.getSearchResult((String)choice,text.getText());
                DefaultTableModel resultModel = new DefaultTableModel();
                resultModel.setDataVector(table,columnNames);
                jTable.setModel(resultModel);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private class MouseListener implements java.awt.event.MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()==1){
                //得到这一行的数据
                int a= jTable.getSelectedRow();
                String[] row= new String[12];
                for (int i=0;i<12;i++){
                    row[i]=jTable.getValueAt(a,i).toString();
                }
                //详情界面打开
                DetailFrame detailFrame = new DetailFrame(row);
                detailFrame.setDefaultCloseOperation(detailFrame.getDefaultCloseOperation());
                detailFrame.setSize(710, 500);
                detailFrame.setVisible(true);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
}
