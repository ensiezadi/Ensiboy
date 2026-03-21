package com.atguigu.java.ai.langchain.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatMemoryProvider = "chatMMProviderXiaozhi",
//        chatModel = "deepseekchat",
        streamingChatModel = "qwenStreamingChatModel",
        tools = "xiaoZhiTools",
//        contentRetriever = "contentRetrieverXiaozhi"
        contentRetriever = "contentRetrieverPinecone"
)

public interface XiaozhiAssisent {
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    Flux<String> chat(@MemoryId long id, @UserMessage String userMessage);
}
