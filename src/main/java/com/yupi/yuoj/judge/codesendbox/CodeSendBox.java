package com.yupi.yuoj.judge.codesendbox;

import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;

/**
 *  代码沙箱接口定义
 *
 */
public interface CodeSendBox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
