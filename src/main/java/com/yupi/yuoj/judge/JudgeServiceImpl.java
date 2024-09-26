package com.yupi.yuoj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.judge.codesendbox.CodeSendBox;
import com.yupi.yuoj.judge.codesendbox.CodeSendboxFactory;
import com.yupi.yuoj.judge.codesendbox.CodeSendboxProxy;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.judge.strategy.JudgeContext;
import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.judge.codesendbox.model.JudgeInfo;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import com.yupi.yuoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.yuoj.service.QuestionService;
import com.yupi.yuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private judgeManage judgeManage;

    /**
     * 代码沙箱类型
     */
    @Value("${codesendbox.value:example}")
    private String type;


    @Override
    public QuestionSubmit doJudge(Long questionSubmitId) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        //根据题目提交id获取题目提交信息 并判空
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //获取题目 并判空
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //获取题目提交信息 并判断 将等待中的状态改成执行中 如果状态不是等待中 那么就不需要再次执行了 防止用户重复提交
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }

        //更新题目状态为 运行中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新失败");
        }

        //调用代码沙箱，获取到执行结果
        //通过配置文件读取代码沙箱类型
        CodeSendBox codeSendBox = CodeSendboxFactory.newInstance(type);
        codeSendBox = new CodeSendboxProxy(codeSendBox);

        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        //获取输入用例 并收集成一个list
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList).build();

        ExecuteCodeResponse executeCodeResponse = codeSendBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutput();
        System.out.println("沙箱输出"+executeCodeResponse);
        //根据沙箱执行结果设置题目判断状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManage.doJudge(judgeContext);
        System.out.println(judgeInfo);
        //修改数据库中的结果
        //更新题目状态为 运行中
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));

        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
