package util;

import vo.StockInfo;
import java.io.File;

public interface FileHandler {

    /**
     * This func gets stock information from the given interfaces path.
     * If interfaces don't exit,or it has a illegal/malformed format, return NULL.
     * The filepath can be a relative path or a absolute path
     * @param filePath
     * @return the Stock information array from the interfaces,or NULL
     */
    StockInfo[] getStockInfoFromFile(String filePath)throws Exception;

}
