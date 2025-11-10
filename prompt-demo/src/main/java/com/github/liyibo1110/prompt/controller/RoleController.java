package com.github.liyibo1110.prompt.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author liyibo
 * @date 2025-11-07 14:09
 */
@RestController
@RequestMapping("/ai")
public class RoleController {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    public RoleController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/roles")
    public AssistantMessage roles(String message, String name, String voice) {
        UserMessage userMessage = new UserMessage(message);
        SystemPromptTemplate template = new SystemPromptTemplate(this.systemResource);
        Message systemMessage = template.createMessage(Map.of("name", name, "voice", voice));
        return this.chatClient.prompt(new Prompt(userMessage, systemMessage))
                .call().chatResponse().getResult().getOutput();
    }
}
