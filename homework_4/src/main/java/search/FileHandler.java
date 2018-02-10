package search;

import vo.Program;

import java.util.List;

/**
 * 请不要对此接口进行任何修改
 */
public interface FileHandler {

    /**
     * 项目保存到文件
     * @param programList 项目集
     * @return 返回插入项目数
     */
    int program2File( List<Program> programList);

}
