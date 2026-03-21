package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.Assistant;
import com.atguigu.java.ai.langchain.assistant.ChatmemoryAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testChatMemory2 {

    @Autowired
    private ChatmemoryAssistant chatmemoryAssistant;

    @Test
    public void testChatMemory(){
        String res1 = chatmemoryAssistant.chat("I am ensi");
        System.out.println(res1);
        String res2 = chatmemoryAssistant.chat("who am i");
        System.out.println(res2);
    }
}
