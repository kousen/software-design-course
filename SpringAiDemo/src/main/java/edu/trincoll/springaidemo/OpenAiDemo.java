package edu.trincoll.springaidemo;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiDemo {
    private final ChatClient chatClient;

    @Autowired
    public OpenAiDemo(AnthropicChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String chat(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

}
