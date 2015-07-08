package me.chrislee.resumeGeneralResolve.extract;

import java.util.Map;

/**
 * Created by ChrisLee.
 */
public interface Extractor {
    public Map<String, Object> extract(String content);
}
