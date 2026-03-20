package com.atguigu.java.ai.langchain.configurations;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddingStoreConfig {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return PineconeEmbeddingStore.builder()
                .apiKey("pcsk_2CEupW_9oG8UAugK4bwBdGPnUdGDQjAXyezqcfVp3ZLKf4EdJq7nk7Y7DbLaAkkyfYRoXr")
                .index("xiaozhi-index") // 如果指定索引不存在，将创建新索引
                .nameSpace("xiaozhi-namespace") // 如果命名空间不存在，将自动创建
                .createIndex(PineconeServerlessIndexConfig.builder()
                        .cloud("gcp")
                        .region("us-central1")
                        .dimension(embeddingModel.dimension())
                        .build())
                .build();
    }

}
