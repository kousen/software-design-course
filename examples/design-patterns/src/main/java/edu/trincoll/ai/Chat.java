package edu.trincoll.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Chat {
    private final Logger logger = LoggerFactory.getLogger(Chat.class);

    private final HttpClient client = HttpClient.newHttpClient();
    public static final String URL = "https://api.openai.com/v1/responses";
    private static final String KEY = System.getenv("OPENAI_API_KEY");
    private static final String DEFAULT_MODEL = "gpt-5-nano";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String chat(String prompt) {
        return chat(DEFAULT_MODEL, prompt);
    }

    public String chat(String model, String prompt) {
        String request = """
                {
                    "model": "%s",
                    "input": "%s"
                }
                """.formatted(model, prompt);
        logger.debug(request);

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(request))
                    .build();

            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            logger.debug("Status: {}", response.statusCode());
            logger.debug("Response:{}", response.body());
            return response.body();
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }
        return "No response returned";
    }
}
