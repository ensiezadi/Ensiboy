//package com.atguigu.java.ai.langchain;
//
//import com.atguigu.java.ai.langchain.assistant.Assistant;
//import dev.langchain4j.model.openai.OpenAiChatModel;
//import dev.langchain4j.service.AiServices;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class toolTest {
//
//    @Autowired
//    @Qualifier("deepseekchat")   // 或者 "openAiChatModel"，看你想测哪一个
//    private OpenAiChatModel chatLanguageModel;
//
//    @Autowired
//    private Assistant assistant;
//
//    @Test
//    public void testAssistant() {
//
//        String answer = assistant.chat("Who am i");
//        System.out.println(">>> 模型回复：" + answer);
//    }
//}
