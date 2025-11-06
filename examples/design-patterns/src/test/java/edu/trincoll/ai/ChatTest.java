package edu.trincoll.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    private final Chat chat = new Chat();

    @Test
    void testChat() {
        String response = chat.chat("gpt-5-mini", "Why is the sky blue?");
        assertNotNull(response);
        assertTrue(response.toLowerCase().contains("scatter"));
    }

    @Test
    void testChatWithDefaultModel() {
        String response = chat.chat("Why is the sky blue?");
        assertNotNull(response);
        assertTrue(response.toLowerCase().contains("scatter"));
        System.out.println(response);
    }

}