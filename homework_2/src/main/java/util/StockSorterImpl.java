package util;

import javafx.util.Pair;
import vo.StockInfo;

public class StockSorterImpl implements StockSorter {
    /**
     * 这个函数用来按照Pair<String,Double>中double的值升序排序
     * @param array stock information
     * @param lo
     * @param hi
     * @returun
     */
    public Pair<String, Double>[] sort(Pair<String, Double>[] array, int lo, int hi) {
        if (lo >= hi) {
            return array;
        }
        int index = partition(array, lo, hi);
        sort(array, lo, index - 1);
        sort(array, index + 1, hi);
        return array;
    }

    /**
     * 这个函数控制升序和降序排序 order为true升序 false降序
     * @param info stock information
     * @param order true:ascending,false:descending
     * @return
     */
    public Pair<String, Double>[] sort(Pair<String, Double>[] info, boolean order) {

        // 升序快速排序
        info = sort(info, 0, info.length - 1);
        // 如果选择降序则先升序排序然后把行颠倒
        if (order == false) {
            Pair<String, Double> temp;
            for (int i = 0; i < info.length / 2; i++) {
                temp = info[i];
                info[i] = info[info.length - 1 - i];
                info[info.length - 1 - i] = temp;
            }
        }

        return info;
    }

    /**
     * 这个函数是快速排序函数用到的划分用的函数
     * @param array
     * @param lo
     * @param hi
     * @return
     */
    public int partition(Pair<String, Double>[] array, int lo, int hi) {
        //固定的切分方式
        Pair<String, Double> key;
        key = array[lo];
        while (lo < hi) {
            while (array[hi].getValue() >= key.getValue() && hi > lo) {//从后半部分向前扫描
                hi--;
            }
            array[lo] = array[hi];
            while (array[lo].getValue() <= key.getValue() && hi > lo) {//从前半部分向后扫描
                lo++;
            }
            array[hi] = array[lo];
        }
        array[hi] = key;
        return hi;
    }
}
