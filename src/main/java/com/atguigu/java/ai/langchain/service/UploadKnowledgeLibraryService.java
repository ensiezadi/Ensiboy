package com.atguigu.java.ai.langchain.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadKnowledgeLibraryService  {
    public void uploadKnowledgeLibrary(MultipartFile[] files);
}
