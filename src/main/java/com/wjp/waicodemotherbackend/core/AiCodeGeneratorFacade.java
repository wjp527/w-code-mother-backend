package com.wjp.waicodemotherbackend.core;

import com.wjp.waicodemotherbackend.ai.AiCodeGeneratorService;
import com.wjp.waicodemotherbackend.ai.model.HtmlCodeResult;
import com.wjp.waicodemotherbackend.ai.model.MultiFileCodeResult;
import com.wjp.waicodemotherbackend.exception.BusinessException;
import com.wjp.waicodemotherbackend.exception.ErrorCode;
import com.wjp.waicodemotherbackend.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * AI 代码生成门面类：统一负责代码生成
 */
@Service
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
