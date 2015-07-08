package me.chrislee.resumeGeneralResolve.extract;

import java.util.Map;

/**
 * Created by ChrisLee.
 * 提取器接口
 */
public interface Extract {
    /**
     * 提取信息
     */
    public Map<String, Object> extract(String content);
}
