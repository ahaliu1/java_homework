package search;

import vo.Program;

/**
 * 请不要对此接口进行任何修改
 */
public interface Parser {

    /**
     * 解析html页面到
     * @param html
     * @return
     */
    Program parseHtml(String html);

}
