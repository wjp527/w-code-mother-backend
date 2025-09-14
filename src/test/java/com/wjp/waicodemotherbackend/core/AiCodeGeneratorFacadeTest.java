package com.wjp.waicodemotherbackend.core;

import com.wjp.waicodemotherbackend.ai.AiCodeGeneratorServiceFactory;
import com.wjp.waicodemotherbackend.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeGeneratorFacadeTest {

    /**
     * 引入 门面类 【代码生成】
     */
    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateAndSaveCode() {
//        File file = aiCodeGeneratorFacade.generateAndSaveCode("生成一个登录页面，不要超过20行代码", CodeGenTypeEnum.HTML);
        File file = aiCodeGeneratorFacade.generateAndSaveCode("生成一个赛博朋克首页，不要超过20行代码", CodeGenTypeEnum.MULTI_FILE);
        Assertions.assertNotNull(file);
    }

    @Test
    void generateAndSaveCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream("生成一个登录页面，不要超过20行代码", CodeGenTypeEnum.HTML);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        // 验证结果
        Assertions.assertNotNull(result);
        // 拼接字符串，获取完整内容
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }
}