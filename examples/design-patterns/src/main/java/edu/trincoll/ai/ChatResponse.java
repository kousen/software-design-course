package edu.trincoll.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChatResponse(
        String id,
        String object,
        @JsonProperty("created_at") long createdAt,
        String status,
        boolean background,
        Billing billing,
        String error,
        @JsonProperty("incomplete_details") String incompleteDetails,
        String instructions,
        @JsonProperty("max_output_tokens") Integer maxOutputTokens,
        @JsonProperty("max_tool_calls") Integer maxToolCalls,
        String model,
        List<Output> output
) {
    public record Billing(String payer) {}

    public record Output(
            String id,
            String type,
            List<String> summary,
            String status,
            List<Content> content,
            String role
    ) {}

    public record Content(
            String type,
            List<String> annotations,
            List<String> logprobs,
            String text
    ) {}
}
