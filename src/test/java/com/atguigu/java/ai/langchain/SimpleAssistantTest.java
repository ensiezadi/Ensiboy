package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.SimpleAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleAssistantTest {

    @Autowired
    private SimpleAssistant assistant;

    @Test
    public void testChat() {
        String res = assistant.chat("Hello, who are you?");
        System.out.println(res);
    }
}
