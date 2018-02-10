package tf_idf;

import javafx.util.Pair;
import util.StockSorter;
import util.StockSorterImpl;
import vo.StockInfo;

import java.util.*;

import static java.lang.StrictMath.log;

public class TF_IDFImpl implements TF_IDF {
    /**
     * this func you need to calculate words frequency , and sort by frequency.
     * you maybe need to use the sorter written by yourself in example 1
     *
     * @param words the word after segment
     * @return a sorted words
     * @see StockSorter
     */
    private util.StockSorter stockSorter;
    @Override
    public Pair<String, Double>[] getResult(List<String> words, StockInfo[] stockInfos){
        stockSorter = new StockSorterImpl();
        // 一行中间由tab分词的词组
        String[] line = new String[words.size()];
        line = words.toArray(line);
        // travelled用于存储已经找到的词组，避免相同的词重复计算
        ArrayList<String> travelled=new ArrayList<>();

        double TfId=0;
        ArrayList<Pair<String, Double>>tfIdfList=new ArrayList<>();
        for(int i = 0;i < words.size();i++) {
            // 将每一个词组分开
            String[] spreateLine = line[i].split("\t");
            for(int j = 0;j < spreateLine.length;j++) {
                if(!travelled.contains(spreateLine[j])){
                    TfId = termFrequence(spreateLine[j], spreateLine)
                            * inverseDocumentFrequency(spreateLine[j], line);
                    tfIdfList.add(new Pair<>(spreateLine[j],TfId));
                    travelled.add(spreateLine[j]);
                }

            }

        }
        // Arraylist转数组
        Pair<String,Double>[] returnValue = new Pair[tfIdfList.size()];
        returnValue = tfIdfList.toArray(returnValue);
        // 排序
        returnValue= stockSorter.sort(returnValue,false);

        return returnValue;
    }
    @Override
    public Double[][] getTFMatrix(List<String> words, StockInfo[] stockInfos) {
        // 一行中间由tab分词的词组
        String[] line = new String[words.size()];
        double TfId = 0;
        line = words.toArray(line);
        ArrayList<String> keyWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            // 一行分开的词汇数组
            String[] spreateLine = line[i].split("\t");
            // 将每行的词汇存放到一个pair数组中以便使用自己写的排序方法
            ArrayList<Pair<String, Double>>tempList=new ArrayList<>();
            ArrayList<String>travelled=new ArrayList<>();
            // 计算每个词的TFIDF值并添加到temp中
            for (int j = 0; j < spreateLine.length; j++) {
                if(!travelled.contains(spreateLine[j])) {
                    TfId = termFrequence(spreateLine[j], spreateLine)
                            * inverseDocumentFrequency(spreateLine[j], line);
                    tempList.add(new Pair<String, Double>(spreateLine[j], TfId));
                    travelled.add(spreateLine[j]);
                }
            }
            Pair<String, Double>[] temp = new Pair[tempList.size()];
            for(int k=0;k<tempList.size();k++){
                temp[k]=tempList.get(k);
            }
            // temp排序降序
            stockSorter=new StockSorterImpl();
            temp = stockSorter.sort(temp,false);
            // 将temp每个元素前20个词加入数组
            for (int k = 0; k < 20; k++) {
                if (temp[k] == null) break;
                keyWords.add(temp[k].getKey());
            }

        }

        // 计算每个content的向量
        Double[][] vector = new Double[words.size()][keyWords.size()];
        for (int i = 0; i < words.size(); i++){

            String[] spreateLine = line[i].split("\t");
            for(int j=0;j<keyWords.size();j++){
                // 因为词频是出现次数/总词数，这里只需要出现次数，所以将词频返回值再乘以总词数获得次数
                vector[i][j]=termFrequence(keyWords.get(j),spreateLine)*spreateLine.length;
            }
        }

        return vector;
    }
    // 计算TF的函数
    public double termFrequence(String str,String[] terms){
        double frequence=0;
        double tf=0;
        //统计词出现的次数
        for (String e:terms) {
            if(str.equals(e)){
                frequence++;
            }
        }
        //计算tf
        tf=frequence/(terms.length);
        return tf;

    }
    // 计算IDF的函数
    public double inverseDocumentFrequency(String str, String[]documents){
        double idf=0;
        // 包含该词的文本数目
        int a=0;
        for (String e:documents) {
            if (e.contains(str)){
                a++;
            }
        }
        idf=log(documents.length/(a+1+0.01));

        return idf;
    }
}
