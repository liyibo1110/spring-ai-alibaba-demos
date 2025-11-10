package com.github.liyibo1110.prompt.controller;

import com.github.liyibo1110.prompt.entity.Completion;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liyibo
 * @date 2025-11-06 16:32
 */
@RestController
@RequestMapping("/ai")
public class StuffController {
    private final ChatClient chatClient;

    @Value("classpath:/docs/bailian.md")
    private Resource docsToStuffResource;

    @Value("classpath:/prompts/qa-prompt.st")
    private Resource qaPromptResource;

    public StuffController(ChatClient.Builder builder) {
       this.chatClient = builder.build();
    }

    @GetMapping(value="/stuff")
    public Completion stuff(String message, boolean stuff) {
        PromptTemplate template = new PromptTemplate(qaPromptResource);
        Map<String, Object> map = new HashMap<>();
        map.put("question", message);
        if(stuff)
            map.put("context", docsToStuffResource);
        else
            map.put("context", "");
        return new Completion(this.chatClient.prompt(template.create(map)).call().content());
    }
}
