package me.chrislee.resumeGeneralResolve.paragraph;

import java.util.Map;

/**
 * Created by ChrisLee.
 * 分段接口，将文档按照配置文件来进行分段
 */
public interface Separate {
    /**
     * 将content分段
     */
    Map<String, String> separate(String content);
}
