package com.atguigu.java.ai.langchain;

import org.junit.jupiter.api.Test;

public class test {
    @Test
    void testEnv() {
        // 区分系统变量和环境变量
        System.out.println("直接读取环境变量: " + System.getenv("DEEPSEEK_API_KEY"));
    }
}
