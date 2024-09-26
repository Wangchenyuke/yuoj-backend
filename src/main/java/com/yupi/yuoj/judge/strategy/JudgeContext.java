package com.yupi.yuoj.judge.strategy;

import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.judge.codesendbox.model.JudgeInfo;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于再策略中传递的参数）
 */
@Data
public class JudgeContext {
   private JudgeInfo judgeInfo;

   private List<String> inputList;

   private List<String> outputList;

   private List<JudgeCase> judgeCaseList;

   private Question question;

   private QuestionSubmit questionSubmit;

}
