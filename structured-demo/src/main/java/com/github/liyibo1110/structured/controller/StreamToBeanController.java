package com.github.liyibo1110.structured.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

/**
 * @author liyibo
 * @date 2025-11-07 14:21
 */
@RestController
@RequestMapping("/ai")
public class StreamToBeanController {
    private final ChatClient chatClient;

    public StreamToBeanController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping(value = "/bean", produces="text/html;charset=utf-8")
    public Bean bean() {
        BeanOutputConverter<Bean> converter = new BeanOutputConverter<>(new ParameterizedTypeReference<>() {});
        Flux<String> flux = this.chatClient.prompt()
                .user(u -> u.text("""
                                          requirement: 请用大概 120 字，作者为 Fox ，为计算机的发展历史写一首现代诗;
                        format: 以纯文本输出 json，请不要包含任何多余的文字——包括 markdown 格式;
                        outputExample: {
                        	 "title": {title},
                        	 "author": {author},
                        	 "date": {date},
                        	 "content": {content}
                        };
                        """))
                .stream()
                .content();
        String result = String.join("\n", Objects.requireNonNull(flux.collectList().block()))
                .replaceAll("\\n", "")
                .replaceAll("\\s+", " ")
                .replaceAll("\"\\s*:", "\":")
                .replaceAll(":\\s*\"", ":\"");
        return converter.convert(result);
    }

    public static class Bean {
        private String title;
        private String author;
        private String date;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
