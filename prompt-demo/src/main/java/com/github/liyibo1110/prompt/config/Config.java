package com.github.liyibo1110.prompt.config;

import com.alibaba.cloud.ai.prompt.ConfigurablePromptTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liyibo
 * @date 2025-11-06 16:52
 */
@Configuration(proxyBeanMethods=false)
public class Config {
    @Bean
    public ConfigurablePromptTemplateFactory configurablePromptTemplateFactory() {
        return new ConfigurablePromptTemplateFactory(); // 没有配置参数
    }

}
