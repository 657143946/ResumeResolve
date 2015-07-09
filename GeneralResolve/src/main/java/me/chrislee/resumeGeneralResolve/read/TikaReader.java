package me.chrislee.resumeGeneralResolve.read;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ChrisLee.
 * Tika读取文档简历
 */
public class TikaReader implements Read {
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * 使用tika读取文档内容
     *
     * @param file File 要读取的文件
     * @return String 读取到的文件内容，文件一定要小于10M
     * @throws IOException
     * @throws TikaException
     * @throws SAXException
     */
    public String read(File file) throws IOException, TikaException, SAXException {
        /**
         * 设置input
         */
        InputStream input = new FileInputStream(file);
        /**
         * 设置meta
         */
        Metadata metadata = new Metadata();
        metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
        metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
        /**
         * 设置handler
         */
        ContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);
        /**
         * 设置context
         */
        ParseContext context = new ParseContext();
        Parser parser = new AutoDetectParser();  // 自动解析器
        context.set(Parser.class, parser);
        /**
         * tika读取
         */
        parser.parse(input, handler, metadata, context);
        return handler.toString();
    }

    /**
     * 实现Read接口
     *
     * @param file 要读取的文件
     * @return 读取到的内容
     */
    @Override
    public String getContent(File file) {
        log.info("读取文件： " + file.getAbsoluteFile());
        String content = "";
        try {
            content = read(file);
        } catch (IOException e) {
            log.error("not found file: " + file.getAbsolutePath());
            log.error(e, e.fillInStackTrace());
        } catch (TikaException e) {
            log.error("tika read error: " + file.getAbsolutePath());
            log.error(e, e.fillInStackTrace());
        } catch (SAXException e) {
            log.error("tika read error: " + file.getAbsolutePath());
            log.error(e, e.fillInStackTrace());
        }
        return content;
    }

    public static void main(String[] args) {
        Read read = new TikaReader();
        File file = new File("C:\\Users\\TEMP.XZ-20131014UNWI.011\\Desktop\\智联张守兵_高级JAVA开发..._中文_20150226_65549432.doc");
        System.out.println(read.getContent(file));
    }
}
