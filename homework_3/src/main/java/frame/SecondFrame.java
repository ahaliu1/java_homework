package frame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SecondFrame extends JFrame {
    private String filePath;//创建的词云图片的路径
    private final JButton save;// 保存按钮

    public SecondFrame(String filePath) {
        super("词云");
        setLayout(null);
        //添加控件
        save = new JButton("保存");
        save.setBounds(600, 100, 90, 20);
        add(save);

        MyJpanel mp = new MyJpanel();
        mp.setBounds(0, 0, 600, 600);
        add(mp);

        //文件路径
        this.filePath = filePath;

        // 创建监听器并注册
        ButtonHandler buttonHandler = new ButtonHandler();
        save.addActionListener(buttonHandler);
    }

    private class MyJpanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            //读取图片并显示
            Image image = null;
            try {
                image = ImageIO.read(new File(filePath));
                g.drawImage(image, 0, 0, 550, 400, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //点击保存按钮
            if (e.getSource() == save) {
                onPressSaveButton();
            }
        }

        //save按钮点击事件
        public void onPressSaveButton(){
            try {
                BufferedImage image = ImageIO.read(new File(filePath));

                JFileChooser chooser = new JFileChooser();

                chooser.setDialogTitle("保存词云");
                chooser.setSelectedFile(new File("词云.png"));

                int option = chooser.showSaveDialog(null);

                //点击JFileChooser的保存按钮
                if (option == JFileChooser.APPROVE_OPTION) {
                    String savePath = chooser.getCurrentDirectory()
                                .getAbsolutePath();
                    String imageName = chooser.getSelectedFile().getName();
                    ImageIO.write(image, "png",
                            new File(savePath + imageName));
                    }
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
