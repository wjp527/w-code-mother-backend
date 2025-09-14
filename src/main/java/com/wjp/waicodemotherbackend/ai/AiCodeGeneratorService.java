package com.wjp.waicodemotherbackend.ai;

import com.wjp.waicodemotherbackend.ai.model.HtmlCodeResult;
import com.wjp.waicodemotherbackend.ai.model.MultiFileCodeResult;
import dev.langchain4j.service.SystemMessage;
import reactor.core.publisher.Flux;

import java.io.File;

/**
 * AI Service 服务
 */
public interface AiCodeGeneratorService {

    /**
     * 生成 HTML 代码
     * @param userMessage 用户消息
     * @return AI 的输出结果
     */
    @SystemMessage("prompt/codegen-html-system-prompt.txt") // 系统提示词
    HtmlCodeResult generateHtmlCode(String userMessage);

    /**
     * 生成 多文件 代码
     * @param userMessage 用户消息
     * @return AI 的输出结果
     */
    @SystemMessage("prompt/codegen-multi-file-system-prompt.txt") // 系统提示词
    MultiFileCodeResult generateMultiFileCode(String userMessage);

    /**
     * SSE 生成 HTML 代码
     * @param userMessage 用户消息
     * @return AI 的输出结果
     */
    @SystemMessage("prompt/codegen-html-system-prompt.txt") // 系统提示词
    Flux<String> generateHtmlCodeStream(String userMessage);

    /**
     * SSE 生成 多文件 代码
     * @param userMessage 用户消息
     * @return AI 的输出结果
     */
    @SystemMessage("prompt/codegen-multi-file-system-prompt.txt") // 系统提示词
    Flux<String> generateMultiFileCodeStream(String userMessage);
}
