package com.github.liyibo1110.ollama.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyibo
 * @date 2025-11-06 16:32
 */
@RestController
@RequestMapping("/ai")
public class ChatController {
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/chat")
    public ChatResponse chat(String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();
                 //.content();
    }
}
