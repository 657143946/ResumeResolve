package me.chrislee.resumeGeneralResolve.resolve;

import me.chrislee.resumeGeneralResolve.preprocess.Preprocess;
import me.chrislee.resumeGeneralResolve.paragraph.Separate;
import me.chrislee.resumeGeneralResolve.paragraph.Separator;
import me.chrislee.resumeGeneralResolve.preprocess.PreprocessManager;
import me.chrislee.resumeGeneralResolve.read.Read;
import me.chrislee.resumeGeneralResolve.read.TikaReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

/**
 * Created by ChrisLee.
 * 解析器
 */
public class Resolver {
    private Logger log = Logger.getLogger(this.getClass());

    private Read read;  // 读文件工具
    private PreprocessManager preprocessManager = PreprocessManager.defaultPrecessManager;  // 文本预处理格式化工具
    private Separate separate;  // 分段工具

    public Read getRead() {
        return read;
    }

    public void setRead(Read read) {
        this.read = read;
    }

    public PreprocessManager getPreprocessManager() {
        return preprocessManager;
    }

    public void setPreprocessManager(PreprocessManager preprocessManager) {
        this.preprocessManager = preprocessManager;
    }

    public Separate getSeparate() {
        return separate;
    }

    public void setSeparate(Separate separate) {
        this.separate = separate;
    }

    public String resolve(File file) {
        log.info("============================================开始读取文本============================================");
        String content = read.getContent(file);
        log.info("\n" + content);
        log.info("============================================开始预处理============================================");
        for (Preprocess preprocess: this.preprocessManager.chain){
            content = preprocess.preprocess(content);
        }
        log.info("\n" + content);
        log.info("============================================开始分块============================================");
        Map<String, String> paragraph = separate.separate(content);
        StringBuilder builder = new StringBuilder();
        for(String key: paragraph.keySet()){
            builder.append("【" + key + "】 " + paragraph.get(key) + "\n");
        }
        log.info("\n" + builder.toString());
        return null;
    }

    public static void main(String[] args) {
        Resolver resolver = new Resolver();

        resolver.setRead(new TikaReader());
        resolver.setSeparate(new Separator());

        File file = new File("C:\\Users\\TEMP.XZ-20131014UNWI.011\\Desktop\\智联张守兵_高级JAVA开发..._中文_20150226_65549432.doc");
        resolver.resolve(file);

    }
}
