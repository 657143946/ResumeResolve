package me.chrislee.resumeGeneralResolve.block;

import java.util.Map;

/**
 * Created by ChrisLee.
 * 分段接口，将文档按照配置文件来进行分段
 */
public interface Separate {
    /**
     * content分段接口
     *
     * @param content String 需要分段文本字符串
     * @return Map 分段后将内容放进各自段落中
     */
    Map<String, String> separate(String content);
}
