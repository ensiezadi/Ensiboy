package com.atguigu.java.ai.langchain; // 确保包名和你的目录匹配

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LLMtest {

    @Qualifier("deepseekchat")
    @Autowired
    private ChatLanguageModel chatLanguageModel; // 统一使用这个变量名

    @Test
    public void testOpenai() { // 加上 public
        // 使用 System.err 确保输出在最显眼的地方
        System.err.println("#############################################");
        System.err.println("###   状态确认：测试方法已经开始执行！    ###");
        System.err.println("#############################################");

        try {
            String res = chatLanguageModel.chat("hello, tell me a joke");
            System.err.println(">>> 最终模型回复如下：");
            System.err.println(res);
        } catch (Exception e) {
            System.err.println(">>> 调用过程中发生异常！");
            e.printStackTrace();
        }
    }
}