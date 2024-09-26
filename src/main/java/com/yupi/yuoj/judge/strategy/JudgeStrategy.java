package com.yupi.yuoj.judge.strategy;

import com.yupi.yuoj.judge.codesendbox.model.JudgeInfo;

/**
 * 判题策略
 *
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
