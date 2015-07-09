package me.chrislee.resumeGeneralResolve.preprocess;

/**
 * Created by ChrisLee.
 * 文本格式化 接口
 */
public interface Preprocess {
    /**
     * 预处理接口
     *
     * @param content String 待处理的文本
     * @return String 处理后的文本
     */
    public String preprocess(String content);
}
