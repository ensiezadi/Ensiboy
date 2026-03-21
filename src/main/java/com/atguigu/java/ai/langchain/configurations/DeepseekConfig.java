package com.atguigu.java.ai.langchain.configurations;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeepseekConfig {

    @Value("${langchain4j.open-ai.chat-model.api-key}")
    private String deepseekApiKey;

    @Bean(name = "deepseekchat")
    public ChatLanguageModel deepseekChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(deepseekApiKey)
                .baseUrl("https://api.deepseek.com")
                .modelName("deepseek-chat")
                .build();
    }

}
