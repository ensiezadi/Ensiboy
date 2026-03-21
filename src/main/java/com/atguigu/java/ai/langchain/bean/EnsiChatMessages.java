package com.atguigu.java.ai.langchain.bean;

import dev.langchain4j.service.MemoryId;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("chat_messages")
public class EnsiChatMessages {

    @Id
    private ObjectId messageId;

    private Integer memoryId;

    private String content;

}