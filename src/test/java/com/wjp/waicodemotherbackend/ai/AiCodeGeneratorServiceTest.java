package com.wjp.waicodemotherbackend.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;
    @Test
    void generateHtmlCode() {
        String result = aiCodeGeneratorService.generateHtmlCode("生成一个不超过20行代码的博客");
        // 为空测试失败，不为空，则成功
        Assertions.assertNotNull(result);
    }

    @Test
    void generateMultiFileCode() {
        String result = aiCodeGeneratorService.generateMultiFileCode("生成一个不超过20行代码的美食介绍文案");
        // 为空测试失败，不为空，则成功
        Assertions.assertNotNull(result);
    }
}