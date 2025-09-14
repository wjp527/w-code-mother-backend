package com.wjp.waicodemotherbackend.core;

import com.wjp.waicodemotherbackend.ai.AiCodeGeneratorService;
import com.wjp.waicodemotherbackend.ai.model.HtmlCodeResult;
import com.wjp.waicodemotherbackend.ai.model.MultiFileCodeResult;
import com.wjp.waicodemotherbackend.exception.BusinessException;
import com.wjp.waicodemotherbackend.exception.ErrorCode;
import com.wjp.waicodemotherbackend.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;

/**
 * AI 代码生成门面类：统一负责代码生成
 */
@Service
@Slf4j
public class AiCodeGeneratorFacade {

    /**
     * AI 代码生成服务
     */
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    /**
     * 统一入口：根据类型生成并保存代码 【主要功能：1、获取AI生成的结果，2、保存结果到文件中】
     * @param userMessage 用户输入
     * @param codeGenTypeEnum 生成类型
     * @return 保存的目录
     */
    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if(codeGenTypeEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未指定生成类型");
        }
        return switch (codeGenTypeEnum) {
            // HTML 模式
            case HTML -> generateAndSaveHtmlCode(userMessage);
            // 多文件 模式
            case MULTI_FILE -> generateAndSaveMultiFileCode(userMessage);
            default -> {
                String errorMessage = "不支持的生成类型: " + codeGenTypeEnum.getValue();
                throw new BusinessException(ErrorCode.PARAMS_ERROR, errorMessage);
            }
        };
    }

    /**
     * 统一入口：根据类型生成并保存代码  SSE流式输出【主要功能：1、获取AI生成的结果，2、保存结果到文件中】
     * @param userMessage 用户输入
     * @param codeGenTypeEnum 生成类型
     * @return 保存的目录
     */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        if(codeGenTypeEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "未指定生成类型");
        }
        return switch (codeGenTypeEnum) {
            // HTML 模式
            case HTML -> generateAndSaveHtmlCodeStream(userMessage);
            // 多文件 模式
            case MULTI_FILE -> generateAndSaveMultiFileCodeStream(userMessage);
            default -> {
                String errorMessage = "不支持的生成类型: " + codeGenTypeEnum.getValue();
                throw new BusinessException(ErrorCode.PARAMS_ERROR, errorMessage);
            }
        };
    }

    /**
     * 生成并保存HTML流式代码块
     * @param userMessage 用户输入
     * @return HTML代码
     */
    private Flux<String> generateAndSaveHtmlCodeStream(String userMessage) {
        // 获取到AI的流式输出
        Flux<String> result = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
        // 字符串拼接器，用于当流式返回所有的代码之后，在保存代码
        StringBuilder codeBuilder = new StringBuilder();
        return result.doOnNext(chunk -> {
            // 实时收集代码片段
            codeBuilder.append(chunk);
        }).doOnComplete(() -> {
            try {
                // 流式返回完成后，保存代码，获取完整的代码块
                String completeHtmlCode = codeBuilder.toString();
                // 解析代码
                HtmlCodeResult htmlCodeResult = CodeParser.parseHtmlCode(completeHtmlCode);
                // 保存代码
                File saveDir = CodeFileSaver.saveHtmlCodeResult(htmlCodeResult);
                log.info("保存成功: {}", saveDir.getAbsolutePath());
            } catch(Exception e) {
                log.error("保存失败: ", e.getMessage());
            }
        });
    }

    /**
     *
     * @param userMessage
     * @return
     */
    private Flux<String> generateAndSaveMultiFileCodeStream(String userMessage) {
        // 调用AI 服务的流式方法，获取AI返回的结果
        Flux<String> result = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
        // 用于累加流式输出的数据
        StringBuilder codeBuilder = new StringBuilder();
        return result.doOnNext(chunk -> {
            // 实时收集代码片段
            codeBuilder.append(chunk);
        }).doOnComplete(() -> {
            try {
                // 转换为 代码块
                String completeCode = codeBuilder.toString();
                // 解析代码块为对象
                MultiFileCodeResult multiFileCodeResult = CodeParser.parseMultiFileCode(completeCode);
                // 保存文件
                File file = CodeFileSaver.saveMultiFileCodeResult(multiFileCodeResult);
                log.info("保存成功: {}", file.getAbsolutePath());
            } catch (Exception e) {
                log.error("保存失败: {}", e.getMessage());
            }
        });

    }


    /**
     * 生成并保存HTML代码
     * @param userMessage
     * @return
     */
    private File generateAndSaveHtmlCode(String userMessage) {
        // 获取AI的输出结果
        HtmlCodeResult htmlCodeResult = aiCodeGeneratorService.generateHtmlCode(userMessage);
        // 保存结果到文件中
        File file = CodeFileSaver.saveHtmlCodeResult(htmlCodeResult);
        return file;
    }

    /**
     * 生成并保存多文件代码
     * @param userMessage
     * @return
     */
    private File generateAndSaveMultiFileCode(String userMessage) {
        // 获取AI的输出结果
        MultiFileCodeResult multiFileCodeResult = aiCodeGeneratorService.generateMultiFileCode(userMessage);
        // 保存结果到文件中
        File file = CodeFileSaver.saveMultiFileCodeResult(multiFileCodeResult);
        return file;
    }



}
