package interfaces;

import vo.StockInfo;


public interface FileHandler {

    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    StockInfo[] getStockInfoFromFile(String filePath)throws Exception;
    /**
     * Set stock information to interfaces.And return lines count.
     * @param filePath
     * @param stocks sorted stock information
     * @return The number of lines written to the interfaces
     */
    int setStockInfo2File(String filePath,StockInfo[] stocks)throws Exception;



}
