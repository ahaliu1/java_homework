package segmenter;


import org.ansj.recognition.impl.StopRecognition;
import vo.StockInfo;
import util.FileHandlerImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
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
    public List<String> getWordsFromInput(StockInfo[] stocks) {
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
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

        //filter.insertStopRegexes(".*?\\w.*?"); //正则表达式,去掉包含数字和字母的

       // filter.insertStopRegexes("\\s"); //正则表达式,去掉空格
        List<String> sepreatedWords = new ArrayList<String>();
        for (int i = 0;i < stocks.length;i++){
            // 分词结果的一个封装，主要是一个List<Term>的terms
            Result result = ToAnalysis.parse(stocks[i].ANSWER).recognition(filter);
            // 拿到每个词，词与词之间tab隔开
            sepreatedWords.add(result.toStringWithOutNature("\t"));
        }
        return sepreatedWords;

    }

    /**
     * 这个函数从stockInfo中获得content并分词
     * @param stocks
     * @return
     */
    public List<String> getContentFromInput(StockInfo[] stocks){
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        StopRecognition filter = new StopRecognition();
        //停词
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
        List<String> sepreatedWords = new ArrayList<String>();
        for (int i = 0;i < stocks.length;i++){
            // 分词结果的一个封装，主要是一个List<Term>的terms
            Result result = ToAnalysis.parse(stocks[i].CONTENT).recognition(filter);
            // 拿到每个词，词与词之间tab隔开
            sepreatedWords.add(result.toStringWithOutNature("\t"));
            }

        return sepreatedWords;

    }

}
