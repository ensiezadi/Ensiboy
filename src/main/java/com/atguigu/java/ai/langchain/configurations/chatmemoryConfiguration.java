package com.atguigu.java.ai.langchain.configurations;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class chatmemoryConfiguration {
    @Bean
    public ChatMemory chatmemory(){
        return MessageWindowChatMemory.withMaxMessages(10);
    }
}
