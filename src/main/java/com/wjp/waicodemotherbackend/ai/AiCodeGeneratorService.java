package com.wjp.waicodemotherbackend.ai;

import dev.langchain4j.service.SystemMessage;

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
    String generateHtmlCode(String userMessage);

    /**
     * 生成 多文件 代码
     * @param userMessage 用户消息
     * @return AI 的输出结果
     */
    @SystemMessage("prompt/codegen-multi-file-system-prompt.txt") // 系统提示词
    String generateMultiFileCode(String userMessage);

}
