package com.yupi.yuoj.judge;

import com.yupi.yuoj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.yuoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.yuoj.judge.strategy.JudgeContext;
import com.yupi.yuoj.judge.strategy.JudgeStrategy;
import com.yupi.yuoj.judge.codesendbox.model.JudgeInfo;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化管理）
 */
@Service
public class judgeManage {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
             judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
