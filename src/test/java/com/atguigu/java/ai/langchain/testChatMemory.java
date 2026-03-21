package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class testChatMemory {

    @Autowired
    private Assistant assistant;
    @Test
    public void testChatMemory(){
        String res1 = assistant.chat("I am ensi");
        System.out.println(res1);

        String res2 = assistant.chat("who am i");
        System.out.println(res2);
    }
}

