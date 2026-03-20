package com.atguigu.java.ai.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LLMtest {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
     public void testOpenai() {
        System.out.println(">>> 准备调用 GPT...");
        String res = openAiChatModel.chat("hello gpt. when is now?");
        System.out.println(">>> GPT回复：" + res);
    }
}
