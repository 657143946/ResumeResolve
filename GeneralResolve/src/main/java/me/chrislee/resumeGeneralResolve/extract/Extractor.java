package me.chrislee.resumeGeneralResolve.extract;

import java.util.Map;

/**
 * Created by ChrisLee.
 * 提取器接口
 */
public interface Extractor {
    public Map<String, Object> extract(String content);
}
