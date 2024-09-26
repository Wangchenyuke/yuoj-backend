package com.yupi.yuoj.judge.codesendbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 代码沙箱响应类定义
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteCodeResponse {
    /**
     * 输出
     */
    private List<String> output;

    /**
     * 接口信息
     */
    private String message;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

}
