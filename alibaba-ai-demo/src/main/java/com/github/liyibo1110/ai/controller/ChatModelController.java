package com.github.liyibo1110.ai.controller;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyibo
 * @date 2025-11-06 16:32
 */
@RestController
@RequestMapping("/ai")
public class ChatModelController {
    private final ChatModel chatModel;

    public ChatModelController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
}
