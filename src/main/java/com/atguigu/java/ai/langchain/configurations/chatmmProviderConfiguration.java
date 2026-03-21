package com.atguigu.java.ai.langchain.configurations;

import com.atguigu.java.ai.langchain.store.MongoChatMemoryStorage;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class chatmmProviderConfiguration {

    @Autowired
    private MongoChatMemoryStorage mongoChatMemoryStorage;

//    @Bean
//    public ChatMemoryProvider chatmmprovider(){
//        return mid -> MessageWindowChatMemory.builder().id(mid).maxMessages(10).build();
//    }

    @Bean
    public ChatMemoryProvider chatmmprovider(){
        return mid -> MessageWindowChatMemory.builder().id(mid).maxMessages(10).chatMemoryStore(mongoChatMemoryStorage).build();
    }
}
