package me.chrislee.resumeGeneralResolve.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ChrisLee
 *         Created by ChrisLee.
 *         读文件工具类
 */
public class ReaderUtils {
    /**
     * 加载资源文件
     */
    public static Properties loadProperties(String filePath) throws IOException {
        if (filePath == null) {
            throw new IOException("loadProperties wrong: filePath can't be null");
        }
        if (!filePath.startsWith("/")) {
            filePath = "/" + filePath;  // 定位到资源根目录，因为使用的是Class.getResourceAsStream()
        }
        /**
         * 加载资源文件
         */
        InputStream is = ReaderUtils.class.getResourceAsStream(filePath);
        Properties props = new Properties();
        props.load(new InputStreamReader(is, "utf-8"));
        return props;
    }
}
