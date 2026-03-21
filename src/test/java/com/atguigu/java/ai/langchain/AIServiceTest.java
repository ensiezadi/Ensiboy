package com.atguigu.java.ai.langchain;

import com.atguigu.java.ai.langchain.assistant.Assistant;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Qualifier;

@SpringBootTest
@ActiveProfiles("test")
public class AIServiceTest {

    @Autowired
    @Qualifier("deepseekchat")
    private OpenAiChatModel chatLanguageModel;

    @Autowired
    private Assistant assistant;

    @Test
    public void testAssistant() {
        // 这行其实没用上返回值，你目前是通过 @Autowired 的 assistant 来用的
        AiServices.create(Assistant.class, chatLanguageModel);

        String answer = assistant.chat("Who am i");
        System.out.println(">>> 模型回复：" + answer);
    }
}
