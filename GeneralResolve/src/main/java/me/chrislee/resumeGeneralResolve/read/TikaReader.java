package me.chrislee.resumeGeneralResolve.read;

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
import java.util.Arrays;

/**
 * Created by ChrisLee.
 * Tika读取文档简历
 */
public class TikaReader implements Read {

    public String read(String fileAbsolutePath) {
        String content = "";
        try {
            Parser parser = new AutoDetectParser();
            InputStream input = null;
            File file = new File(fileAbsolutePath);
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            input = new FileInputStream(file);
            ContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);
            ParseContext context = new ParseContext();
            context.set(Parser.class,parser);
            parser.parse(input,handler, metadata,context);


            try {
                metadataToString(metadata);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            content = handler.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("file not found："+fileAbsolutePath);
        } catch (TikaException e) {
            System.out.println("不能解析");
//			throw new IllegalArgumentException("can't read file through Tika："+fileAbsolutePath);
        } catch (SAXException e) {
            throw new IllegalArgumentException("can't parse file through Tika："+fileAbsolutePath);
        }
        return content;

    }
    public String read(File file) {
        String content = "";
        try {
            Parser parser = new AutoDetectParser();
            InputStream input = null;
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_ENCODING, "utf-8");
            metadata.set(Metadata.RESOURCE_NAME_KEY, file.getName());
            input = new FileInputStream(file);
            ContentHandler handler = new BodyContentHandler();//当文件大于100000时，new BodyContentHandler(1024*1024*10);
            ParseContext context = new ParseContext();
            context.set(Parser.class,parser);
            parser.parse(input,handler, metadata,context);
            try {
                metadataToString(metadata);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            content = handler.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("file not found：");
        } catch (TikaException e) {
            throw new IllegalArgumentException("can't read file through Tika：");
        } catch (SAXException e) {
            System.out.println("不能解析");
//			throw new IllegalArgumentException("can't parse file through Tika：");
        }
        return content;

    }
    private String metadataToString(Metadata m) throws Exception {

        StringBuilder metadataBuffer = new StringBuilder();

        String[] names = m.names();
        Arrays.sort(names);
        for (String name : names) {
            metadataBuffer.append(name);
            metadataBuffer.append(": ");
            metadataBuffer.append(m.get(name));
            metadataBuffer.append("\n");
        }

        return metadataBuffer.toString();
    }

    @Override
    public String getContent(File file) {
        return null;
    }
}
