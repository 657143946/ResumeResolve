package me.chrislee.resumeGeneralResolve.formatter;

import java.util.HashMap;
import java.util.Map;


/**
 * @author ChrisLee
 *         默认的简历内容格式化预处理器
 */
public class DefaultFormatter implements Formatter {

    public static Map<String, String> strangeCharRegex2NormalMappingTalbe = new HashMap<String, String>() {{
        put("\u00A0+", " ");
        put("~+", "");
        put("·+", "");
        put("　+", " ");
    }};

    public static Map<String, String> symbolMappingTable = new HashMap<String, String>() {{
        put("，", ",");
        put("。", ".");
        put("：", ":");
        put("；", ";");
        put("（", "(");
        put("）", ")");
        put("”", "\"");
        put("“", "\"");
        put("、", " ");
    }};


    @Override
    public String format(String content) {
        content = DefaultFormatter.removeStrangeChar(content);
        content = DefaultFormatter.removeJs(content);
        content = DefaultFormatter.removeCss(content);
        content = DefaultFormatter.removeXml(content);
        content = DefaultFormatter.removeHtmlTag(content);
        content = DefaultFormatter.chineseSymbol2EnglishSymbol(content, symbolMappingTable);
        content = DefaultFormatter.removeUnnecessaryLines(content);
        content = DefaultFormatter.fixWrongLine(content);
        return "\n" + content;
    }


    /**
     * 去除诡异字符
     */
    public static String removeStrangeChar(String content) {
        if (content == null) {
            throw new IllegalArgumentException("class FileReader: method removeUnnecessaryLines: content not be null");
        }
        return regexReplaceAll(content, strangeCharRegex2NormalMappingTalbe);
    }


    /**
     * 将换行符前后的空字符全部变为单独的换行符
     */
    public static String removeUnnecessaryLines(String content) {
        if (content == null) {
            throw new IllegalArgumentException("class FileReader: method removeUnnecessaryLines: content not be null");
        }
        return content.replaceAll("\\n\\s*", "\n");
    }


    /**
     * 中文符号转英文符号
     */
    public static String chineseSymbol2EnglishSymbol(String content, Map<String, String> symbolMappingTable) {
        for (String key : symbolMappingTable.keySet()) {
            content = content.replaceAll(key, symbolMappingTable.get(key));
        }
        return content;
    }


    /**
     * 通过正则，将匹配的字符替换成指定字符串
     */
    public static String regexReplaceAll(String content, Map<String, String> regex2StringMappingTable) {
        for (String key : regex2StringMappingTable.keySet()) {
            content = content.replaceAll(key, regex2StringMappingTable.get(key));
        }
        return content;
    }


    /**
     * 去除JS内容
     */
    public static String removeJs(String content) {
        content = content.replaceAll("<script", "~~<script").replaceAll("</script>", "</script>~~");
        content = content.replaceAll("~~[^~]*~~", "");
        return content;
    }


    /**
     * 去除CSS内容
     */
    public static String removeCss(String content) {
        content = content.replaceAll("<style", "~~<style").replaceAll("</style>", "</style>~~");
        content = content.replaceAll("~~[^~]*~~", "");
        return content;
    }


    /**
     * 去除xml内容
     */
    public static String removeXml(String content) {
        content = content.replaceAll("<xml", "~~<xml").replaceAll("</xml>", "</xml>~~");
        content = content.replaceAll("~~[^~]*~~", "");
        return content;
    }


    /**
     * 去除HTML标签
     */
    public static String removeHtmlTag(String content) {
        content = content.replaceAll("<p[^<>]*>", "\n");
        content = content.replaceAll("</p[^<>]*>", "\n");
        content = content.replaceAll("</span[^<>]*>", " ");
        content = content.replaceAll("<span[^<>]*>", " ");
        content = content.replaceAll("<[^<>]*>", "");
        content = content.replaceAll("(&nbsp;)+", " ");
        return content;
    }


    /**
     * 去除错误的换行,以及没有必要的空字符,去掉标签
     */
    public static String fixWrongLine(String content) {
        if (content == null) {
            throw new IllegalArgumentException("class FileReader: method removeWrongLinefeed: content not be null");
        }
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
        content = removeUnnecessaryLines(content);
        return content;
    }

    public static void main(String[] args) {
        String test = "1: \n2";
        test = test.replaceAll("\\s*: {2,}", ":无");
    }

}
