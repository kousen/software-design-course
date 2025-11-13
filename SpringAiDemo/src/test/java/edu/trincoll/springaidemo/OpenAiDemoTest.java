package edu.trincoll.springaidemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiDemoTest {
    @Autowired
    private OpenAiDemo openAiDemo;

    @Test
    void testChat() {
        String response = openAiDemo.chat("Why is the sky blue?");
        System.out.println(response);
        assertTrue(response.toLowerCase().contains("scattering"));
    }

}