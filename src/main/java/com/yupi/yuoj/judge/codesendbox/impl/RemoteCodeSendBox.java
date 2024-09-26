package com.yupi.yuoj.judge.codesendbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.nodes.Http;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.judge.codesendbox.CodeSendBox;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;

import java.nio.charset.Charset;

/**
 * 远程代码沙箱 （最终用于业务的代码沙箱）
 */
public class RemoteCodeSendBox implements CodeSendBox {
    //在请求头中添加鉴权信息 防止发布到公网不安全
    public static final String AUTH_REQUEST_HEADER = "auth";

    public static final String AUTH_REQUEST_SECRET = "secretKey";



    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String requestJson = JSONUtil.toJsonStr(executeCodeRequest);
        String md5Str = DigestUtil.md5Hex(AUTH_REQUEST_SECRET.getBytes(Charset.forName("UTF-8")));
        HttpResponse response = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,md5Str)
                .body(requestJson)
                .execute();
        String responseBody = response.body();
        ExecuteCodeResponse executeCodeResponse = JSONUtil.toBean(responseBody, ExecuteCodeResponse.class);
        if (StrUtil.isBlank(responseBody)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR);
        }
        return executeCodeResponse;
    }
}
