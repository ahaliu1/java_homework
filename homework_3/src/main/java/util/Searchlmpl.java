package util;

import java.util.ArrayList;
import java.util.List;


public class Searchlmpl implements Search {
    @Override
    public int[] getSimilarityPoint(String[] keyWords, List<String> dictionary) {
        int[] points = new int[dictionary.size()];

        //遍历过的词的数组
        List<String> travelled = new ArrayList<String>();

        for (int i = 0; i < dictionary.size(); i++) {
            String[] spreateLine = dictionary.get(i).split("\t");
            travelled.clear();

            for (String keyWord : keyWords) {
                for (int j = 0; j < spreateLine.length; j++) {
                    if (keyWord.equals(spreateLine[j])) {
                        //已经出现过的词+1分
                        if (travelled.contains(keyWord)) {
                            points[i]++;
                        } else {
                            //将关键字标红
                            dictionary.set(i, dictionary.get(i).replace(keyWord,
                                    "<span><font color=red>" + keyWord
                                            + "</font></span>"));

                            //没有出现过的词+200分 字+50分
                            if (keyWord.length() > 1) {
                                points[i] = points[i] + 200;
                            } else {
                                points[i] = points[i] + 50;
                            }

                            travelled.add(keyWord);
                        }
                    }
                }
            }
        }

        return points;
    }
}
