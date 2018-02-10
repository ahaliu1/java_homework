package recommend;

import javafx.util.Pair;
import segmenter.ChineseSegmenter;
import segmenter.ChineseSegmenterImpl;
import tf_idf.TF_IDF;
import tf_idf.TF_IDFImpl;
import util.StockSorter;
import util.StockSorterImpl;
import vo.StockInfo;
import vo.UserInterest;
;import java.util.ArrayList;
import java.util.List;

public class RecommenderImpl implements Recommender {
    private ChineseSegmenter segmenter;

    private TF_IDF tf_idf;

    private StockSorter stockSorter;
    /**
     * this function need to calculate stocks' content similarity,and return the similarity matrix
     *
     * @param stocks stock info
     * @return similarity matrix
     */
    @Override
    public double[][] calculateMatrix(StockInfo[] stocks) {

        tf_idf = new TF_IDFImpl();
        segmenter = new ChineseSegmenterImpl();
        List<String>words = segmenter.getContentFromInput(stocks);
        Double[][] vectorMatrix = tf_idf.getTFMatrix(words,stocks);
        double[][] cosMatrix = new double[vectorMatrix.length][vectorMatrix.length];
        // 遍历每一行
        for (int i = 0;i < vectorMatrix.length;i++){
            // 遍历每一行
            for (int j= 0;j<vectorMatrix.length;j++){
                // 矩阵对角线等于0
                if(i==j){
                    cosMatrix[i][j]=0;
                }
                // 计算两个向量的cos
                else {
                cosMatrix[i][j]=calculateCos(vectorMatrix[i],vectorMatrix[j]);
                }
        }
    }
        return cosMatrix;
    }

    /**
     * 这个函数用来计算两个向量的余弦值
     * @param vec1
     * @param vec2
     * @return
     */
    double calculateCos(Double[] vec1,Double[] vec2){
        double numerator = 0;// 分子
        for(int i = 0;i < vec1.length;i++){
            numerator += vec1[i]*vec2[i];
        }
        double denominator = 0;// 分母
        double sum1 = 0;
        double sum2 = 0;
        for(int i = 0;i < vec1.length;i++){
            sum1 += vec1[i]*vec1[i];
            sum2 += vec2[i]*vec2[i];
        }
        denominator = Math.sqrt(sum1)*Math.sqrt(sum2);
        double cos = numerator/denominator;
        return cos;
    }

    /**
     * this function need to recommend the most possibility stock number
     *
     * @param matrix       similarity matrix
     * @param userInterest user interest
     * @return commend stock number
     */
    @Override
    public double[][] recommend(double[][] matrix, UserInterest[] userInterest) {
        double[][] recommendMatrix = new double[500][10];
        ArrayList<Pair<String,Double>>li = new ArrayList<>();
        // 遍历user
        for(int i = 0;i < 500;i++){
            li.clear();
            // 遍历article
            for(int j = 0;j < 60;j++){
                if(userInterest[i].userInterest[j]==0){
                    // 为了使用StockSortor中的排序方法所以将第j+1(j为下标所以要加1）篇文章的编号j转换成string传进去，反正排序不用
                    li.add(new Pair<String, Double>(String.valueOf(j+1),
                            likeLevel(j,matrix,userInterest[i]))) ;
                }
            }
                //arraylist转array
                Pair<String,Double>[] pairArray = new Pair[li.size()];
                pairArray =li.toArray(pairArray);
                // 排序
                stockSorter=new StockSorterImpl();
                pairArray= stockSorter.sort(pairArray,false);// 降序
                // 排序后每个用户取10条推荐
                for(int l=0;l<10;l++){
                recommendMatrix[i][l]=Integer.parseInt(pairArray[l].getKey());
                }
        }
        return recommendMatrix;
    }

    /**
     * 这个函数是协同过滤算法的实现
     * @param article
     * @param cosmatrix
     * @param userInterest
     * @return
     */
    double likeLevel(int article,double[][] cosmatrix,UserInterest userInterest){
        double likeLevel = 0;
        for(int i = 0;i < 60;i++){
            // 如果用户看过第i篇文章就在cosmatrix中找到第i篇文章和第article篇文章
            // 对应的cos值加到衡量喜爱程度的likelevel中
            if(userInterest.userInterest[i] == 1){
                likeLevel += cosmatrix[i][article];
            }
        }
        return likeLevel;
    }

}
