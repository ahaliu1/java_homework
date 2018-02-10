package frame;

import util.*;
import vo.StockInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FirstFrame extends JFrame {
    private final JButton openFileButton;
    private final JButton searchButton;
    private final JTextField text;
    private final JList list;
    private final JLabel label;
    private List<String> dictionaryTitle; // 存放Title的list，词之间用\t分隔开
    private List<String> dictionaryContent; // 存放content的list，词之间用\t分隔开
    private List<String> dictionaryAnswer; // 存放answer的list，词之间用\t分隔开
    private String wordsFromInput; // 搜索框输入的字符串

    public FirstFrame() {
        super("文件搜索");
        setLayout(null);
        //添加控件
        text = new JTextField(30);
        text.setBounds(20, 20, 300, 20);
        add(text);
        searchButton = new JButton("搜索");
        searchButton.setBounds(350, 20, 90, 20);
        add(searchButton);
        openFileButton = new JButton("打开文件");
        openFileButton.setBounds(350, 90, 90, 20);
        add(openFileButton);
        list = new JList();
        list.setBounds(20, 90, 300, 150);
        add(list);
        label = new JLabel();
        label.setBounds(350, 100, 90, 60);
        add(label);

        // 滚动条
        JScrollPane jsp = new JScrollPane(list);
        jsp.setBounds(20, 90, 300, 150);
        add(jsp);

        //创建按钮监听器并注册
        ButtonHandler handler = new ButtonHandler();
        searchButton.addActionListener(handler);
        openFileButton.addActionListener(handler);

        //创建Jlist的监听器并注册
        MouseListener listSelectionListener = new MouseListener();
        list.addMouseListener(listSelectionListener);
    }

    //按钮监听器
    private class ButtonHandler implements ActionListener {
        private FileHandler fileHandler;
        private ChineseSegmenter segmenter;
        private Search search;
        private Sort sort;

        @Override
        public void actionPerformed(ActionEvent e) {
            segmenter = new ChineseSegmenterImpl();

            if (e.getSource() == openFileButton) {
                label.setText("文件打开中...");
                onPressOpenFileButton();
            }

            if (e.getSource() == searchButton) {
                onPressSearchButton();
            }
        }

        //openFile按钮点击事件
        private void onPressOpenFileButton() {
            fileHandler = new FileHandlerImpl();

            //文件选择器
            JFileChooser fd = new JFileChooser();
            fd.showOpenDialog(null);

            //得到文件
            File f = fd.getSelectedFile();
            StockInfo[] stockInfos = new StockInfo[0];

            //解析文件
            try {
                stockInfos = fileHandler.getStockInfoFromFile(f.getAbsolutePath());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            //分词 分词后list中按照 title content answer顺序依次存放
            List<String> wordsFromFile = segmenter.getWordsFromFile(stockInfos);
            dictionaryTitle = new ArrayList<String>();
            dictionaryContent = new ArrayList<String>();
            dictionaryAnswer = new ArrayList<String>();

            //将title content answer分开存放
            for (int i = 0; i < wordsFromFile.size(); i = i + 3) {
                dictionaryTitle.add(wordsFromFile.get(i));
                dictionaryContent.add(wordsFromFile.get(i + 1));
                dictionaryAnswer.add(wordsFromFile.get(i + 2));
            }
            if(f.isFile()){
                label.setText("文件打开成功");
            }
        }

        //serch按钮点击事件
        private void onPressSearchButton() {
            //得到搜索关键词并分词
            String keyWords = text.getText();
            wordsFromInput = segmenter.getWordsFromInput(keyWords);

            String[] keyWordsArray = wordsFromInput.split("\t");

            search = new Searchlmpl();
            sort = new SortLmpl();

            // 搜索得到每个新闻与搜索关键词的相似程度
            int[] titlePoints = search.getSimilarityPoint(keyWordsArray,
                    dictionaryTitle);
            int[] answerPoints = search.getSimilarityPoint(keyWordsArray,
                    dictionaryAnswer);
            int[] contentPoints = search.getSimilarityPoint(keyWordsArray,
                    dictionaryContent);
            int[] points = new int[titlePoints.length];

            for (int i = 0; i < titlePoints.length; i++) {
                points[i] = answerPoints[i] + contentPoints[i] +
                        titlePoints[i];
            }

            // 将得分数组排序并得到前十条新闻
            int[] topIds = sort.getTopIds(points);

            //要展示在JList中的新闻
            String[] contentShownInList = new String[topIds.length * 5];
            int j = 0;

            //去掉dictionary中的\t用于显示 深拷贝
            List<String> dictionaryAnswerCopy = new ArrayList<String>();
            List<String> dictionaryContentCopy = new ArrayList<String>();
            List<String> dictionaryTitleCopy = new ArrayList<String>();

            for (int i = 0; i < dictionaryAnswer.size(); i++) {
                dictionaryContentCopy.add(dictionaryContent.get(i)
                        .replace("\t", ""));
                dictionaryTitleCopy.add(dictionaryTitle.get(i).replace("\t", ""));
                dictionaryAnswerCopy.add(dictionaryAnswer.get(i)
                        .replace("\t", ""));
            }

            for (int i = 0; i < (topIds.length * 5); i = i + 5) {
                contentShownInList[i] = "ID:" + String.valueOf(topIds[j]) + 1;
                contentShownInList[i + 1] = "<html><p>" + "TITLE:" +
                        dictionaryTitleCopy.get(topIds[j]) + "</p></html>";
                contentShownInList[i + 2] = "<html><p>" + "CONTENT:" +
                        dictionaryContentCopy.get(topIds[j]) + "</p></html>";
                contentShownInList[i + 3] = "<html><p>" + "ANSWER:" +
                        dictionaryAnswerCopy.get(topIds[j]) + "</p></html>";
                contentShownInList[i + 4] = " ";
                j++;
            }

            //JList展示
            list.setListData(contentShownInList);
        }
    }

    //Jlist鼠标监听器
    private class MouseListener implements java.awt.event.MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            //双击事件
            if (e.getClickCount() == 2) {
                //将dictionaryContent对应下标中词按\t分开
                String[] s = dictionaryContent.get(list.getSelectedIndex() / 5)
                        .split("\t");
                int a = list.getSelectedIndex() / 5;
                List<String> temp = new ArrayList<String>();

                //去除用于字体颜色变化的html标签
                for (int i = 0; i < s.length; i++) {
                    s[i] = s[i].replace("<span><font color=red>", "");
                    s[i] = s[i].replace("</font></span>", "");
                    temp.add(s[i]);
                }

                // 按照content创建词云
                Color[] colors = new Color[10];

                for (int i = 0; i < colors.length; i++) {
                    colors[i] = new Color((new Double(Math.random() * 128)).intValue() +
                            128,
                            (new Double(Math.random() * 128)).intValue() + 128,
                            (new Double(Math.random() * 128)).intValue() + 128);
                }

                WordCloudBuilder.buildWordCouldByWords(200, 200, 4, 20, 10,
                        temp, new Color(-1), "data.png", colors);

                //创建词云展示的界面
                SecondFrame secondFrame = new SecondFrame("data.png");
                secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                secondFrame.setSize(710, 500);
                secondFrame.setVisible(true);
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
