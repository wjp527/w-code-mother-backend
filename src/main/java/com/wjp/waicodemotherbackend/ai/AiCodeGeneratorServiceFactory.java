package com.wjp.waicodemotherbackend.ai;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI 服务创建工厂
 */
@Configuration // 项目初始化的时候，Spring会扫描到Configuration注解，会自动创建 AiServices对象
public class AiCodeGeneratorServiceFactory {

    @Resource
    private ChatModel chatModel;

    /**
    * 流式模式
    */
    @Resource
    private StreamingChatModel streamingChatModel;

    /**
     * 创建AI 代码生成器服务
     * 当项目启动的时候，创建什么样的对象
     * @return
     */
    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
//        // 非流式
//        return AiServices.create(
//                // 使用哪个AI Service服务
//                AiCodeGeneratorService.class,
//                // 使用哪个大模型
//                chatModel
//        );

        // SSE 流式
        return AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .build();


    }









}
