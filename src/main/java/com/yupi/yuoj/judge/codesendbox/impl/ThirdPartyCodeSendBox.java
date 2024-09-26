package com.yupi.yuoj.judge.codesendbox.impl;

import com.yupi.yuoj.judge.codesendbox.CodeSendBox;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（网上现成的代码沙箱的实现）
 */
public class ThirdPartyCodeSendBox implements CodeSendBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
