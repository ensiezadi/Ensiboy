package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.ChatmemoryAssistant;
import com.atguigu.java.ai.langchain.assistant.ChatmemorySepratorAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testChatMemory3 {

    @Autowired
    private ChatmemorySepratorAssistant chatmemorySepratorAssistant;

    @Test
    public void testChatMemory(){
        String res1 = chatmemorySepratorAssistant.chat(1,"I am ensi");
        System.out.println(res1);
        System.out.println("===");
        System.out.println("===");
        String res2 = chatmemorySepratorAssistant.chat(1,"who am i");
        System.out.println(res2);
        System.out.println("===");
        System.out.println("===");
        String res3 = chatmemorySepratorAssistant.chat(3,"who am i");
        System.out.println(res3);
        System.out.println("===");
        System.out.println("===");
    }
}
