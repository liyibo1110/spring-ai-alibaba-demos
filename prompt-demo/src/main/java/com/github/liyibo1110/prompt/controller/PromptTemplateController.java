package com.github.liyibo1110.prompt.controller;

import com.alibaba.cloud.ai.prompt.ConfigurablePromptTemplate;
import com.alibaba.cloud.ai.prompt.ConfigurablePromptTemplateFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author liyibo
 * @date 2025-11-06 16:32
 */
@RestController
@RequestMapping("/ai")
public class PromptTemplateController {
    private final ChatClient chatClient;
    private final ConfigurablePromptTemplateFactory promptTemplateFactory;

    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;
    public PromptTemplateController(ChatClient.Builder builder,
                                    ConfigurablePromptTemplateFactory promptTemplateFactory) {
       this.chatClient = builder.build();
       this.promptTemplateFactory = promptTemplateFactory;
    }

    @GetMapping("/prompt")
    public AssistantMessage prompt(String topic, String adjective) {
        PromptTemplate template = new PromptTemplate(this.jokeResource);
        Prompt prompt = template.create(Map.of("topic", topic, "adjective", adjective));
        return this.chatClient.prompt(prompt)
                .call().chatResponse().getResult().getOutput();
    }

    @GetMapping(value="/promptTemplate")
    public AssistantMessage promptTemplate(String author) {
        ConfigurablePromptTemplate template = this.promptTemplateFactory.getTemplate("test-template");
        if(template == null)
            template = promptTemplateFactory.create("test-template", "请列出{author}最著名的三本书。");

        Prompt prompt;
        if(StringUtils.isNotBlank(author))
            prompt = template.create(Map.of("author", author));
        else
            prompt = template.create();

        return this.chatClient.prompt(prompt)
                .call().chatResponse().getResult().getOutput();
    }
}
