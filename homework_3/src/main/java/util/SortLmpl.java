package util;

import javafx.util.Pair;


public class SortLmpl implements Sort {
    @Override
    //将points数组排序并返回前10个的下标
    public int[] getTopIds(int[] points) {
        Pair<Integer, Integer>[] idsAndPoints = new Pair[points.length];

        for (int i = 0; i < points.length; i++) {
            idsAndPoints[i] = new Pair<Integer, Integer>(i, points[i]);
        }

        // 排序
        idsAndPoints = sort(idsAndPoints, 0, idsAndPoints.length - 1);

        int[] topIds = new int[10];

        //前十个的下标存入返回
        for (int i = 0; i < topIds.length; i++) {
            topIds[i] = idsAndPoints[i].getKey();
        }

        return topIds;
    }

    //快速排序
    private Pair<Integer, Integer>[] sort(Pair<Integer, Integer>[] array,
                                          int lo, int hi) {
        if (lo >= hi) {
            return array;
        }

        int index = partition(array, lo, hi);
        sort(array, lo, index - 1);
        sort(array, index + 1, hi);

        return array;
    }

    private int partition(Pair<Integer, Integer>[] array, int lo, int hi) {
        //固定的切分方式
        Pair<Integer, Integer> key;
        key = array[lo];

        while (lo < hi) {
            while ((array[hi].getValue() <= key.getValue()) && (hi > lo)) { //从后半部分向前扫描
                hi--;
            }

            array[lo] = array[hi];

            while ((array[lo].getValue() >= key.getValue()) && (hi > lo)) { //从前半部分向后扫描
                lo++;
            }

            array[hi] = array[lo];
        }

        array[hi] = key;

        return hi;
    }
}
