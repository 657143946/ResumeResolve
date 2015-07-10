package me.chrislee.resumeGeneralResolve.preprocess;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChrisLee.
 * 内建的文本预处理器
 */
public class BuildinPreprocess {
    private final static Logger log = Logger.getLogger(BuildinPreprocess.class);

    /**
     * 内建预处理器
     * 去除HTML标签（换行和空格会等效转换, span会转为空格）
     */
    public static final Preprocess removeHtml = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("去除HTML标签");
            content = content.replaceAll("<p[^<>]*>", "\n");  // p标签换成换行
            content = content.replaceAll("</p[^<>]*>", "\n");  // p标签换成换行
            content = content.replaceAll("</span[^<>]*>", " ");  // span标签换成空格
            content = content.replaceAll("<span[^<>]*>", " ");  // span标签换成空格
            content = content.replaceAll("<br>", "\n");  // br标签换成换行
            content = content.replaceAll("</br>", "\n");  // br标签换成换行
            content = content.replaceAll("<[^<>]*>", "");  // 去除所有其他HTML标签
            content = content.replaceAll("(&nbsp;)+", " ");  // html特殊空格字符换成空格
            return content;
        }
    };

    /**
     * 内建预处理器
     * 去除诡异字符
     */
    public static final Preprocess removeStrangeChar = new Preprocess() {
        public Map<String, String> strangeCharRegex2NormalMappingTalbe = new HashMap<String, String>() {{
            put("\u00A0+", " ");  // 多个诡异空白字符=空格
            put("~+", "");  // 多个~字符=空字符
            put("·+", "");  // 多个·字符=空字符（·字符不是英语中的点'.'字符）
            put("　+", " ");  // 多个　诡异空白字符=空格
        }};

        @Override
        public String preprocess(String content) {
            log.info("去除诡异字符");
            for (String key : strangeCharRegex2NormalMappingTalbe.keySet()) {
                content = content.replaceAll(key, strangeCharRegex2NormalMappingTalbe.get(key));
            }
            return content;
        }
    };

    /**
     * 内建预处理器
     * 去除JS代码
     */
    public static final Preprocess removeJs = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("去除JS代码");
            content = content.replaceAll("<script", "~~<script").replaceAll("</script>", "</script>~~");
            content = content.replaceAll("~~[^~]*~~", "");
            return content;
        }
    };

    /**
     * 内建预处理器
     * 去除CSS代码
     */
    public static final Preprocess removeCss = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("去除CSS代码");
            content = content.replaceAll("<style", "~~<style").replaceAll("</style>", "</style>~~");
            content = content.replaceAll("~~[^~]*~~", "");
            return content;
        }
    };

    /**
     * 内建预处理器
     * 去除XML代码
     */
    public static final Preprocess removeXml = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("去除XML代码");
            content = content.replaceAll("<xml", "~~<xml").replaceAll("</xml>", "</xml>~~");
            content = content.replaceAll("~~[^~]*~~", "");
            return content;
        }
    };

    /**
     * 内建预处理器
     * 中文标点符号转换成英文标点符号
     */
    public static final Preprocess chineseSymbol2EnglishSymbol = new Preprocess() {
        public Map<String, String> symbolMappingTable = new HashMap<String, String>(){{
            put("，", ",");  // 逗号
            put("。", ".");  // 句号
            put("：", ":");  // 冒号
            put("；", ";");  // 分号
            put("（", "(");  // 左括号
            put("）", ")");  // 右括号
            put("”", "\"");  // 分号
            put("“", "\"");  // 分号
            put("、", " ");  // 顿号
        }};
        @Override
        public String preprocess(String content) {
            log.info("中文标点符号转换成英文标点符号");
            for(String key: symbolMappingTable.keySet()){
                content = content.replaceAll(key, symbolMappingTable.get(key));
            }
            return content;
        }
    };

    /**
     * 内建预处理器
     * 将换行符前后的空字符全部变为单独的换行符
     */
    public final static Preprocess removeUnnecessaryLines = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("将换行符前后的空字符全部变为单独的换行符");
            return content.replaceAll("\\n\\s+", "\n");
        }
    };

    /**
     * 内建预处理器
     * 将读出来的文本中出现的错误换行全部修复
     */
    public final static Preprocess fixWrongLine = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("将读出来的文本中出现的错误换行全部修复");
            // :换错行or多余空格
            content = content.replaceAll("\\s*: {4,}", ":无");
            content = content.replaceAll("\\s*:\\s*", ":");
            content = content.replaceAll("\\s*：\\s*", ":");
            // +换错行or多余空格
            content = content.replaceAll("\\s*\\+\\s*", "+");
            // -_-——等换错行or多余空格
            content = content.replaceAll("\\s*-\\s*", "-");
            content = content.replaceAll("\\s*——\\s*", "-");
            content = content.replaceAll("\\s*—\\s*", "-");
            content = content.replaceAll("\\s*_\\s*", "_");
            // |换错行or多余空格
            content = content.replaceAll("\\s*\\|\\s*", "|");
            // /换错行or多余空格
            content = content.replaceAll("\\s*/\\s*", "/");
            // ,换错行or多余空格
            content = content.replaceAll("\\s*,\\s*", ",");
            content = content.replaceAll("\\s*，\\s*", ",");
            // 多个制表符换成一个
            content = content.replaceAll("\\t+", "\t");
            // 多个空格换成一个
            content = content.replaceAll(" +", " ");
            // 2个以上的等号以及相邻的空字符去掉
            content = content.replaceAll("={2,}\\s+", "");
            content = content.replaceAll("\\s+={2,}", "");
            // 去掉空行
            content = content.replaceAll("\\n{2,}", "\n");
            // 再次检查，去掉空行
            content = BuildinPreprocess.removeUnnecessaryLines.preprocess(content);
            return content;
        }
    };

    /**
     * 内建预处理器
     * 去除文本首尾空字符
     */
    public final static Preprocess trim = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("去除文本首尾空字符");
            return content.trim();
        }
    };

    public final static Preprocess addFirstLine = new Preprocess() {
        @Override
        public String preprocess(String content) {
            log.info("在整个文本第一个字符添加一个换行");
            return "\n" + content;
        }
    };



}
