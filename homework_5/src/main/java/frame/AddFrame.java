package frame;

import database.DataBase;
import vo.Program;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFrame extends JFrame {
    private final JButton insertButton;
    private final JTable jTable;

    public AddFrame() {
        super("添加");
        setLayout(null);

        insertButton = new JButton("添加");
        insertButton.setBounds(500, 100, 150, 40);
        add(insertButton);

        String[] coloumNames = {"", ""};
        String[] rowNames = {"Id", "country", "university", "school", "program_name", "homepage", "location", "email", "phone_number", "degree", "ddl_with_aid", "ddl_without_add"};
        Object a[][] = new Object[12][2];
        for (int i = 0; i < 12; i++) {
            a[i][0] = rowNames[i];
            a[i][1] = "";
        }

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setDataVector(a, coloumNames);

        jTable = new JTable(tableModel);
        jTable.setBounds(40, 40, 400, 360);
        jTable.setRowHeight(30);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        add(jTable);

        ButtonHandler buttonHandler = new ButtonHandler();
        insertButton.addActionListener(buttonHandler);
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == insertButton) {
                //将用户输入数据存入program类对象
                Program program =new Program();
                program.setId(jTable.getValueAt(0,1).toString());
                program.setCountry(jTable.getValueAt(1,1).toString());
                program.setUniversity(jTable.getValueAt(2,1).toString());
                program.setSchool(jTable.getValueAt(3,1).toString());
                program.setProgramName(jTable.getValueAt(4,1).toString());
                program.setHomepage(jTable.getValueAt(5,1).toString());
                program.setLocation(jTable.getValueAt(6,1).toString());
                program.setEmail(jTable.getValueAt(7,1).toString());
                program.setPhoneNumber(jTable.getValueAt(8,1).toString());
                program.setDegree(jTable.getValueAt(9,1).toString());
                program.setDeadlineWithAid(jTable.getValueAt(10,1).toString());
                program.setDeadlineWithoutAid(jTable.getValueAt(11,1).toString());

                DataBase dataBase = new DataBase();
                try {
                    //插入
                    dataBase.insert(program);

                    //添加成功提示
                    JOptionPane.showMessageDialog(null,"添加成功","标题",JOptionPane.WARNING_MESSAGE);

                }catch (NullPointerException e1){
                    //未连接数据库添加造成空指针提示
                    JOptionPane.showMessageDialog(null,"您尚未连接数据库","标题",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}

