//package com.atguigu.java.ai.langchain;
//
//import com.atguigu.java.ai.langchain.bean.EnsiChatMessages;
//import org.bson.types.ObjectId;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class MongoCrudTest {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Test
//    public void testCreateChatMessage() {
//        EnsiChatMessages chatMessage = new EnsiChatMessages();
//        chatMessage.setContent("{\"message\":\"Hello, world!\"}");
//
//        EnsiChatMessages savedMessage = mongoTemplate.save(chatMessage);
//        assertNotNull(savedMessage.getMessageId());
//        System.out.println("Created ChatMessage: " + savedMessage);
//    }
//
//    @Test
//    public void testReadChatMessage() {
//        // 先插入一条，再读取
//        EnsiChatMessages chatMessage = new EnsiChatMessages();
//        chatMessage.setContent("{\"message\":\"Hello, world!\"}");
//        EnsiChatMessages saved = mongoTemplate.save(chatMessage);
//
//        ObjectId id = saved.getMessageId();
//        EnsiChatMessages found = mongoTemplate.findById(id, EnsiChatMessages.class);
//
//        assertNotNull(found);
//        assertEquals("{\"message\":\"Hello, world!\"}", found.getContent());
//        System.out.println("Read ChatMessage: " + found);
//    }
//
//    @Test
//    public void testUpdateChatMessage() {
//        // 先插入一条
//        EnsiChatMessages chatMessage = new EnsiChatMessages();
//        chatMessage.setContent("{\"message\":\"Hello, world!\"}");
//        EnsiChatMessages saved = mongoTemplate.save(chatMessage);
//
//        // 再读取并更新
//        ObjectId id = saved.getMessageId();
//        EnsiChatMessages toUpdate = mongoTemplate.findById(id, EnsiChatMessages.class);
//        assertNotNull(toUpdate);
//
//        toUpdate.setContent("{\"message\":\"Updated content\"}");
//        EnsiChatMessages updatedMessage = mongoTemplate.save(toUpdate);
//
//        assertEquals("{\"message\":\"Updated content\"}", updatedMessage.getContent());
//        System.out.println("Updated ChatMessage: " + updatedMessage);
//    }
//
//    @Test
//    public void testDeleteChatMessage() {
//        // 1. 先创建一个临时的对象并保存
//        EnsiChatMessages temp = new EnsiChatMessages();
//        temp.setContent("{\"message\":\"delete me\"}");
//        EnsiChatMessages saved = mongoTemplate.save(temp);
//        ObjectId id = saved.getMessageId(); // 获取刚生成的 ID
//
//        assertNotNull(id, "保存后的ID不应为空");
//
//        // 2. 执行删除
//        mongoTemplate.remove(saved);
//
//        // 3. 验证是否真的没了
//        EnsiChatMessages found = mongoTemplate.findById(id, EnsiChatMessages.class);
//        assertNull(found, "删除后应该找不到该文档");
//        System.out.println("成功删除了自动生成的 ID: " + id);
//    }
//}
