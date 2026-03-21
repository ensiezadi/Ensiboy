package com.atguigu.java.ai.langchain.controller;

import com.atguigu.java.ai.langchain.assistant.XiaozhiAssisent;
import com.atguigu.java.ai.langchain.bean.ChatFormDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Tag(name = "xiaozhi")
@RequestMapping("/xiaozhi")
public class xiaozhicontroller {

    @Autowired
    XiaozhiAssisent xiaozhiAssisent;

//    @PostMapping("guiguxiaozhi")
//    @Operation(summary = "硅谷小智")
//    public String chat(@RequestBody ChatFormDTO chatFormDTO){
//        return xiaozhiAssisent.chat(chatFormDTO.getMemoryId(),
//                chatFormDTO.getUserMessage());
//    }

    @Operation(summary = "对话")
    @PostMapping(value = "/chat",produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@RequestBody ChatFormDTO chatFormDTO) {
        return xiaozhiAssisent.chat(chatFormDTO.getMemoryId(),
                chatFormDTO.getMessage());
    }
}
