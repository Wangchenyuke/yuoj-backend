package com.yupi.yuoj.judge.codesendbox.impl;

import com.yupi.yuoj.judge.codesendbox.CodeSendBox;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.judge.codesendbox.model.JudgeInfo;
import com.yupi.yuoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.yuoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSendBox implements CodeSendBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> input = executeCodeRequest.getInputList();
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();


        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutput(inputList);
        executeCodeResponse.setMessage(code);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());


        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
