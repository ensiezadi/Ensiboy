package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.ChatmemorySepratorAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class testChatMemory4 {

    @Autowired
    private ChatmemorySepratorAssistant chatmemorySepratorAssistant;

    @Test
    public void testChatMemory(){
        String answer = chatmemorySepratorAssistant.chat(4, "1+2等于几，475695037565的平方根是多少？");
        System.out.println(answer);
        System.out.println("===");
        System.out.println("===");
//        String res2 = chatmemorySepratorAssistant.chat(1,"who am i");
//        System.out.println(res2);
        System.out.println("===");
        System.out.println("===");
//        String res3 = chatmemorySepratorAssistant.chat(3,"who am i");
//        System.out.println(res3);
        System.out.println("===");
        System.out.println("===");
    }
}
