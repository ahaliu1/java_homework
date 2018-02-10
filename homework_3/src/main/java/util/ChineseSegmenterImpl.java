package util;

import org.ansj.domain.Result;

import org.ansj.recognition.impl.StopRecognition;

import org.ansj.splitWord.analysis.ToAnalysis;

import vo.StockInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ChineseSegmenterImpl implements ChineseSegmenter {
    /**
     * this func will get chinese word from a list of stocks. You need analysis stocks' answer and get answer word.
     * And implement this interface in the class : ChineseSegmenterImpl
     * Example: 我今天特别开心 result : 我 今天 特别 开心
     *
     * @param stocks stocks info
     * @return chinese word
     * @see ChineseSegmenterImpl
     */
    @Override
    public List<String> getWordsFromFile(StockInfo[] stocks) {
        Set<String> expectedNature = new HashSet<String>() {

            {
                add("n");
                add("v");
                add("vd");
                add("vn");
                add("vf");
                add("vx");
                add("vi");
                add("vl");
                add("vg");
                add("nt");
                add("nz");
                add("nw");
                add("nl");
                add("ng");
                add("userDefine");
                add("wh");
            }
        };

        StopRecognition filter = new StopRecognition();
        //标点符号
        filter.insertStopNatures("w");
        //助词
        filter.insertStopNatures("u");
        //数词
        filter.insertStopNatures("m");
        //连词
        filter.insertStopNatures("c");
        //字符串
        filter.insertStopNatures("x");
        //拟声词
        filter.insertStopNatures("o");
        //语气词
        filter.insertStopNatures("y");
        //叹词
        filter.insertStopNatures("e");

        filter.insertStopNatures("null");

        List<String> sepreatedWords = new ArrayList<String>();
        Result result;

        for (int i = 0; i < stocks.length; i++) {
            result = ToAnalysis.parse(stocks[i].getTitle()).recognition(filter);
            sepreatedWords.add(result.toStringWithOutNature("\t"));
            result = ToAnalysis.parse(stocks[i].getContent()).recognition(filter);
            sepreatedWords.add(result.toStringWithOutNature("\t"));
            result = ToAnalysis.parse(stocks[i].getAnswer()).recognition(filter);
            sepreatedWords.add(result.toStringWithOutNature("\t"));
        }

        return sepreatedWords;
    }

    @Override
    public String getWordsFromInput(String keyWords) {
        Set<String> expectedNature = new HashSet<String>() {

            {
                add("n");
                add("v");
                add("vd");
                add("vn");
                add("vf");
                add("vx");
                add("vi");
                add("vl");
                add("vg");
                add("nt");
                add("nz");
                add("nw");
                add("nl");
                add("ng");
                add("userDefine");
                add("wh");
            }
        };

        StopRecognition filter = new StopRecognition();
        //标点符号
        filter.insertStopNatures("w");
        //助词
        filter.insertStopNatures("u");
        //数词
        filter.insertStopNatures("m");
        //连词
        filter.insertStopNatures("c");
        //字符串
        filter.insertStopNatures("x");
        //拟声词
        filter.insertStopNatures("o");
        //语气词
        filter.insertStopNatures("y");
        //叹词
        filter.insertStopNatures("e");

        filter.insertStopNatures("null");


        Result result = ToAnalysis.parse(keyWords).recognition(filter);
        String sepreatedWords = result.toStringWithOutNature("\t");

        return sepreatedWords;
    }
}
