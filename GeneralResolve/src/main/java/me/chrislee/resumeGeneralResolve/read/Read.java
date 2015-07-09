package me.chrislee.resumeGeneralResolve.read;

import java.io.File;

/**
 * Created by ChrisLee.
 * 读取文档内容的接口
 */
public interface Read {
    /**
     * 读取文档内容接口
     *
     * @param file File 要读取的文件
     * @return String 文件内容
     */
    public String getContent(File file);
}
