package com.atguigu.java.ai.langchain.store;

import com.atguigu.java.ai.langchain.bean.EnsiChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStorage implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        EnsiChatMessages res = mongoTemplate.findOne(query, EnsiChatMessages.class);
        if(res == null){
            return new LinkedList<>();
        }
        String content = res.getContent();
        List<ChatMessage> messages = ChatMessageDeserializer.messagesFromJson(content);

        // 返回一个可变列表
        return new LinkedList<>(messages);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        String temp = ChatMessageSerializer.messagesToJson(list);
        Update update = new Update();
        update.set("content", temp);
        mongoTemplate.upsert(query, update, EnsiChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, EnsiChatMessages.class);
    }
}
