package com.atguigu.java.ai.langchain.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "deepseekchat"
)
public interface SimpleAssistant {

    String chat(@UserMessage String message);
}
