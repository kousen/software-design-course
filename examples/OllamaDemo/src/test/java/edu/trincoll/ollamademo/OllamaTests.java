package edu.trincoll.ollamademo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OllamaTests {
    @Autowired
    private OllamaChatModel model;

    private ChatClient client;

    @BeforeEach
    void setup() {
        client = ChatClient.create(model);
    }

    @Test
    void testChat() {
        String prompt = "Why is the sky blue?";
        String response = client.prompt()
                .user(prompt)
                .call()
                .content();
        System.out.println("Prompt: " + prompt);
        System.out.println("Response: " + response);
    }

}
