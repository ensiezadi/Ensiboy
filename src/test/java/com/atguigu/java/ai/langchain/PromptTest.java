package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.ChatmemoryAssistant;
import com.atguigu.java.ai.langchain.assistant.ChatmemorySepratorAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PromptTest {
    @Autowired
    private ChatmemoryAssistant chatmemorySepratorAssistant;

    @Test
    public void testSystemMessage() {

        String answer = chatmemorySepratorAssistant.chat("今天是几月");

        String answer1 = chatmemorySepratorAssistant.chat("我是ensi");

        String answer2 = chatmemorySepratorAssistant.chat("今年22了");

        System.out.println(answer);

    }
}
