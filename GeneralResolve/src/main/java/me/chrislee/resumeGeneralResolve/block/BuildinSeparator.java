package me.chrislee.resumeGeneralResolve.block;

import me.chrislee.resumeGeneralResolve.utils.ReaderUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ChrisLee.
 * 内建的分块器
 */
public class BuildinSeparator implements Separate {
    private final static Logger log = Logger.getLogger(BuildinSeparator.class);

    public Map<String, String> titleTagsMapping = new HashMap<String, String>();
    public Map<String, String> tagTitleMapping = new HashMap<String, String>();

    private String paragraphTitleFilePath = "/resolve/paragraph/paragraph.properties";
    private String paragraphTitleRegex = "/resolve/paragraph/regex.properties";

    public String paragraphRegex = null;
    public String addParagraphTagRegex = null;
    public String addParagraphTag = null;
    public String missProfileRegex = null;
    public String englishResumeRegex = null;

    public Map<String, String> paragraph = new HashMap<String, String>();

    @Override
    public Map<String, String> separate(String content) {
        log.info("对文本开始分块");
        try {
            this.loadParagraphTitle(paragraphTitleFilePath);
            this.loadRegex(paragraphTitleRegex);
        } catch (IOException e) {
            log.error("加载分块配置文件失败：" + e.getMessage());
            log.error(e, e.fillInStackTrace());
        }
        content = englishResumeExtract(content);

        content = addParagraphTitleTag(content);
        paragraphSeparate(content);
        nullProfileAndRetryExtractProfile(content);
        mergeSimilarityInfo(paragraph);
        return paragraph;
    }


    /**
     * 同类信息合并在一个键值对中
     */
    private void mergeSimilarityInfo(Map<String, String> map){
        String contact = map.get("contact");
        if (contact != null){
            map.put("profile", map.get("profile") + contact);
        }
    }


    /**
     * 如果没有找到简历基本信息，将没有标签的第一段划分成基本信息，并且放入到paragraph中
     */
    private void nullProfileAndRetryExtractProfile(String contentWithParagraphTag){
        if (!this.paragraph.containsKey("profile")){
            Pattern profilePattern = Pattern.compile(missProfileRegex);
            Matcher profileMatcher = profilePattern.matcher(contentWithParagraphTag);
            if (profileMatcher.find()){
                addParagraph("profile", profileMatcher.group(1));
            }
        }
    }


    /**
     * 分段处理，将添加了段标签的文字进行正则匹配提取段落，并且放入到paragraph中
     */
    private void paragraphSeparate(String contentWithParagraphTag){
        Pattern pattern = Pattern.compile(paragraphRegex);
        Matcher matcher = pattern.matcher(contentWithParagraphTag);
        while(matcher.find()){
            try{
                String name = matcher.group(1);
                String value = matcher.group(2);
                addParagraph(name, value);
            } catch(Exception e){

            }
        }
    }


    /**
     * 添加段落
     */
    public void addParagraph(String name, String content){
        if (content == null){
            return ;
        } else {
            content = content.trim();
            if (content.equals("")){
                return ;
            }
        }
        if (this.paragraph.containsKey(name)){
            this.paragraph.put(name, this.paragraph.get(name)+"\n"+content);
        }else{
            this.paragraph.put(name, content);
        }
    }


    /**
     * 英文简历分割,将英文简历当成单独一段放入paragraph，并且将原来的内容中去除掉英文简历段落
     */
    public String englishResumeExtract(String content){
        Pattern pattern = Pattern.compile(this.englishResumeRegex);
        Matcher matcher = pattern.matcher(content);
        String englishResume = "";
        if (matcher.find()){
            englishResume = matcher.group(1);
            addParagraph("english", matcher.group(1));
        }
        return content.replace(englishResume, "");
    }



    /**
     * 为内容中符合标签的地方加入特殊标记，便于稍后正则处理
     */
    public String addParagraphTitleTag(String content){
        for (String tag: this.tagTitleMapping.keySet()){
            content = content.replaceAll(this.addParagraphTagRegex.replace("$tag", tag), this.addParagraphTag.replace("$tag", this.tagTitleMapping.get(tag)));
        }
        return content;
    }


    /**
     * 加载标签文件
     */
    public void loadParagraphTitle(String filePath) throws IOException{
        Properties paragraph = ReaderUtils.loadProperties(filePath);

        this.titleTagsMapping = new HashMap<>();
        for (Object key: paragraph.keySet()) {
            String k = (String)key;
            this.titleTagsMapping.put(k, paragraph.getProperty(k, ""));
        }

        for (String name: this.titleTagsMapping.keySet()){
            for(String tag: this.titleTagsMapping.get(name).split(";")){
                this.tagTitleMapping.put(tag, name);
            }
        }
    }


    /**
     * 加载分段要使用的正则
     */
    public void loadRegex(String filePath) throws IOException{
        Properties regexes = ReaderUtils.loadProperties(filePath);
        this.addParagraphTag = regexes.getProperty("add_paragraph_tag");
        this.addParagraphTagRegex = regexes.getProperty("add_paragraph_tag_regex");
        this.paragraphRegex = regexes.getProperty("paragraph_separate_regex");
        this.missProfileRegex = regexes.getProperty("miss_profile_regex");
        this.englishResumeRegex = regexes.getProperty("english_regex");
    }


    public String getParagraphTitleFilePath() {
        return paragraphTitleFilePath;
    }

    public void setParagraphTitleFilePath(String paragraphTitleFilePath) {
        this.paragraphTitleFilePath = paragraphTitleFilePath;
    }

    public String getParagraphTitleRegex() {
        return paragraphTitleRegex;
    }

    public void setParagraphTitleRegex(String paragraphTitleRegex) {
        this.paragraphTitleRegex = paragraphTitleRegex;
    }





    public static void main(String[] args) throws IOException{
//		DefaultParagraphSeparator para = new DefaultParagraphSeparator();

//		System.out.println(para.tagTitleMapping);
//		para.separate("10年以上工作经验 | 男 | 39岁（1974年9月25日） |");

//        Map<String, String> map = ReaderUtil.readPropertyResource("paragraph_regex.properties");
//        System.out.println(map);

    }
}
