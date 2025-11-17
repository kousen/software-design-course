package edu.trincoll.game.config;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration for ChatClient beans.
 * <p>
 * This configuration demonstrates:
 * <p>
 * - Spring's Dependency Injection
 * - Factory pattern for creating ChatClient instances
 * - How to configure multiple AI providers
 * <p>
 * Each ChatClient bean is named so it can be injected by qualifier.
 * <p>
 * Design Pattern: FACTORY
 * <p>
 * - Creates different ChatClient instances
 * - Encapsulates model-specific configuration
 * - Allows swapping models via dependency injection
 */
@Configuration
public class ChatClientConfig {

    /**
     * Creates ChatClient for OpenAI (GPT models).
     * <p>
     * Configuration comes from application.yml:
     * <p>
     * - spring.ai.openai.api-key
     * - spring.ai.openai.chat.options.model
     *
     * @param chatModel the auto-configured OpenAI chat model
     * @return ChatClient configured for OpenAI
     */
    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }

    /**
     * Creates ChatClient for Anthropic (Claude models).
     * <p>
     * Configuration comes from application.yml:
     * <p>
     * - spring.ai.anthropic.api-key
     * - spring.ai.anthropic.chat.options.model
     *
     * @param chatModel the auto-configured Anthropic chat model
     * @return ChatClient configured for Anthropic
     */
    @Bean
    public ChatClient anthropicChatClient(AnthropicChatModel chatModel) {
        return ChatClient.builder(chatModel).build();
    }
}
