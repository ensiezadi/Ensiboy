package com.atguigu.java.ai.langchain.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatMemory = "chatmemory",
        chatModel = "deepseekchat"
)
public interface ChatmemoryAssistant {
    @UserMessage("你是我的好朋友，请用上海话回答问题。{{message}}")
    String chat(@V("message") String message);
}
