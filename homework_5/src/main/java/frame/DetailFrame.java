package frame;

import database.DataBase;
import vo.Program;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailFrame extends JFrame {
    private final JButton updateButton;
    private final JButton deleteButton;
    private final JTable jTable;
    public DetailFrame(String[] row){

        super("详情");
        setLayout(null);

        updateButton = new JButton("更改");
        updateButton.setBounds(500, 140 ,150, 40);
        add(updateButton);

        deleteButton = new JButton("删除");
        deleteButton.setBounds(500, 40 ,150, 40);
        add(deleteButton);

        //如果是云数据库则不允许更改和删除
        //演示时云数据库开启所以又不用了。。。
        /*if (isRemoteDB==true){
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }*/

        //去掉html标签
        for (int i = 0; i < row.length; i++) {
            row[i] = row[i].replace("<html>","");
            row[i] = row[i].replace("<span><font color=red>", "");
            row[i] = row[i].replace("</font></span>", "");
        }

        String[] coloumNames={"","detail"};
        String[] rowNames ={"Id","country","university","school","program_name","homepage","location","email","phone_number","degree","ddl_with_aid","ddl_without_add"};
        Object a[][]= new Object[12][2];
        for(int i =0;i<12;i++){
            a[i][0]=rowNames[i];
            a[i][1]=row[i];
        }

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setDataVector(a,coloumNames);

        jTable = new JTable(tableModel);
        jTable.setBounds(40, 40, 400, 360);
        jTable.setRowHeight(30);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        add(jTable);

        ButtonHandler buttonHandler= new ButtonHandler();
        deleteButton.addActionListener(buttonHandler);
        updateButton.addActionListener(buttonHandler);
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()== updateButton){
                //用户更改存入program
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

                //更新
                dataBase.update(program);

                //更新成功提示框
                JOptionPane.showMessageDialog(null,"更改成功","标题",JOptionPane.WARNING_MESSAGE);

            }
            if (e.getSource()==deleteButton){
                DataBase dataBase = new DataBase();

                //通过id删除
                dataBase.delete(jTable.getValueAt(0, 1).toString());

                //删除成功提示框
                JOptionPane.showMessageDialog(null,"删除成功","标题",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
