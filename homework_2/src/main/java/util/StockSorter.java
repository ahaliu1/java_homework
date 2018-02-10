package util;

import javafx.util.Pair;
import vo.StockInfo;


public interface StockSorter {

    /**
     * Accepting series of stock info, sorting them ascending according to their comment length.
     * List.sort() or Arrays.sort() are not allowed.
     * You have to write your own algorithms,Bubble sort、quick sort、merge sort、select sort,etc.
     *
     * @param array stock information
     * @return sorted stock
     */
    public Pair<String,Double>[] sort(Pair<String,Double>[] array,int lo,int hi);

    /**
     * Accepting series of stock info, sorting them ascending, descending order.
     * @param info stock information
     * @param order true:ascending,false:descending
     * @return sorted stock
     */
    public Pair<String, Double>[] sort(Pair<String, Double>[] info, boolean order);

}
