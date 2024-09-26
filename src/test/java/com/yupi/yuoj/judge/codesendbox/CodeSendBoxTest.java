package com.yupi.yuoj.judge.codesendbox;

import com.yupi.yuoj.common.PageRequest;
import com.yupi.yuoj.judge.codesendbox.impl.ExampleCodeSendBox;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesendbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ImageBanner;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.util.function.TupleExtensionsKt;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSendBoxTest {
    @Value("${codesendbox.value:example}")
    private String value;

    @Test
    void executeCode() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            CodeSendBox codeSendBox = CodeSendboxFactory.newInstance(type);

            String code = "int main(){}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList).build();

            ExecuteCodeResponse executeCodeResponse = codeSendBox.executeCode(executeCodeRequest);
            Assertions.assertNotNull(executeCodeResponse);
        }
    }

    @Test
    void executeCodeByValue() {
        //通过配置文件读取代码沙箱类型
        CodeSendBox codeSendBox = CodeSendboxFactory.newInstance(value);
        String code = "int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList).build();

        ExecuteCodeResponse executeCodeResponse = codeSendBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);

    }

    @Test
    void executeCodeByProxy() {
        //通过配置文件读取代码沙箱类型
        CodeSendBox codeSendBox = CodeSendboxFactory.newInstance(value);
        CodeSendboxProxy codeSendboxProxy = new CodeSendboxProxy(codeSendBox);

        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果\"+(a+b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList).build();

        ExecuteCodeResponse executeCodeResponse = codeSendboxProxy.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
        Assertions.assertNotNull(executeCodeResponse);

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            CodeSendBox codeSendBox = CodeSendboxFactory.newInstance(type);

            String code = "int main(){}";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList).build();

            ExecuteCodeResponse executeCodeResponse = codeSendBox.executeCode(executeCodeRequest);
            Assertions.assertNotNull(executeCodeResponse);
        }
    }
}