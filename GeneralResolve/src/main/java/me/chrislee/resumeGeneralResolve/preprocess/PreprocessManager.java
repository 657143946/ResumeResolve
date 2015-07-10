package me.chrislee.resumeGeneralResolve.preprocess;

import me.chrislee.resumeGeneralResolve.chain.Chain;

/**
 * Created by ChrisLee.
 * 预处理管理器
 */
public class PreprocessManager {
    /**
     * 预处理流水线
     */
    public final Chain<Preprocess> chain = new Chain<>();

    /**
     * 默认的预处理管理器
     */
    public final static PreprocessManager defaultPrecessManager = new PreprocessManager();

    static {
        defaultPrecessManager.chain.add(BuildinPreprocess.removeJs);
        defaultPrecessManager.chain.add(BuildinPreprocess.removeCss);
        defaultPrecessManager.chain.add(BuildinPreprocess.removeXml);
        defaultPrecessManager.chain.add(BuildinPreprocess.removeHtml);
        defaultPrecessManager.chain.add(BuildinPreprocess.removeStrangeChar);
        defaultPrecessManager.chain.add(BuildinPreprocess.chineseSymbol2EnglishSymbol);
        defaultPrecessManager.chain.add(BuildinPreprocess.removeUnnecessaryLines);
        defaultPrecessManager.chain.add(BuildinPreprocess.fixWrongLine);
        defaultPrecessManager.chain.add(BuildinPreprocess.trim);
        defaultPrecessManager.chain.add(BuildinPreprocess.addFirstLine);
    }



}
