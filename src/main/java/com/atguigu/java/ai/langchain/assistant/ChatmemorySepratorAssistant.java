package com.atguigu.java.ai.langchain.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import org.apache.catalina.User;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatMemoryProvider = "chatmmprovider",
        chatModel = "deepseekchat",
        tools = "xiaoZhiTools"
)
public interface ChatmemorySepratorAssistant {
//    @SystemMessage("你是一个智能助手，请用福建话回答问题。today is {{current_date}}")
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    String chat(@MemoryId int id, @UserMessage String userMessage);
}
