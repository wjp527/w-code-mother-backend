package com.wjp.waicodemotherbackend.core;

import com.wjp.waicodemotherbackend.ai.AiCodeGeneratorServiceFactory;
import com.wjp.waicodemotherbackend.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

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
}