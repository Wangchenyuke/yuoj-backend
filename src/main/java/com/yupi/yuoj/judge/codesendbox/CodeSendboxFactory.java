package com.yupi.yuoj.judge.codesendbox;

import com.yupi.yuoj.judge.codesendbox.impl.ExampleCodeSendBox;
import com.yupi.yuoj.judge.codesendbox.impl.RemoteCodeSendBox;
import com.yupi.yuoj.judge.codesendbox.impl.ThirdPartyCodeSendBox;
import org.checkerframework.common.reflection.qual.NewInstance;

/**
 * 代码沙箱工厂 （根据字符串创建指定代码沙箱工厂）
 */
public class CodeSendboxFactory {
    /**
     * 创建 代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSendBox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSendBox();
            case "remote":
                return new RemoteCodeSendBox();
            case "thirdparty":
                return new ThirdPartyCodeSendBox();
            default:
                return new ExampleCodeSendBox();
        }
    }
}
