package com.yupi.yuoj.judge.codesendbox;

import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码沙箱代理类（正原有的沙箱功能上增强 但是不侵入原有的代码）
 */
@Slf4j
@AllArgsConstructor
public class CodeSendboxProxy implements CodeSendBox{

    /**
     * 通过代理类 对executeCode方法进行日志的输出
     */
    private final CodeSendBox codeSendBox;


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息"+executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSendBox.executeCode(executeCodeRequest);
        log.info("代码沙箱的响应信息"+executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
