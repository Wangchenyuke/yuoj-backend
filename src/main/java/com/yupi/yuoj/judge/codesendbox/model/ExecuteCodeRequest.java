package com.yupi.yuoj.judge.codesendbox.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱接受类定义
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecuteCodeRequest {
    /**
     * 一组输入用例
     */
    private List<String> inputList;
    /**
     * 代码
     */
    private String code;
    /**
     * 编程语言
     */
    private String language;
}
