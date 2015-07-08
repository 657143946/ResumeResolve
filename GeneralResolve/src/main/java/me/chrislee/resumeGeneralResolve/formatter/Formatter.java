package me.chrislee.resumeGeneralResolve.formatter;

/**
 * @author ChrisLee
 *         Created by ChrisLee.
 *         提取各种doc文件后的预处理格式化
 */
public interface Formatter {
    /**
     * 对content字符串进行格式化预处理
     */
    public String format(String content);
}
