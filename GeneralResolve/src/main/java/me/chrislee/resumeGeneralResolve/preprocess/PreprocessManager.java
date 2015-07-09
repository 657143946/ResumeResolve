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
}
