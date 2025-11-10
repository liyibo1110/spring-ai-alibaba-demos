package com.github.liyibo1110.structured.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeResponseFormat;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyibo
 * @date 2025-11-07 14:21
 */
@RestController
@RequestMapping("/ai")
public class StreamToJsonController {
    private final ChatClient chatClient;

    public StreamToJsonController(ChatClient.Builder builder) {
        DashScopeResponseFormat format = new DashScopeResponseFormat();
        format.setType(DashScopeResponseFormat.Type.JSON_OBJECT);
        this.chatClient = builder.defaultOptions(
                DashScopeChatOptions.builder()
                        .withTopP(0.7)
                        .withResponseFormat(format)
                        .build()
        ).build();
    }

    @GetMapping(value = "/json", produces="text/html;charset=utf-8")
    public String json() {
        return this.chatClient.prompt("你好，请以JSON格式介绍你自己！")
                .call().content();
    }
}
