package com.github.liyibo1110.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
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
public class RagController {
    @Autowired
    private ChatClient chatClient;

    @Autowired
    private VectorStore store;

    @GetMapping("/chat")
    public String chat(String message) {
       return this.chatClient.prompt()
               .user(message).advisors(new QuestionAnswerAdvisor(this.store))
               .call().content();
    }
}
