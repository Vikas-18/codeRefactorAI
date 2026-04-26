package com.example.autoGithubCommit.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LLMService {

    private final WebClient webClient;

    @Value("${hf.token}")
    private String token;

    private final ObjectMapper mapper =
            new ObjectMapper();

    public String improveCode(String code) throws Exception {

        Map<String,Object> body = Map.of(
                "model",
                "deepseek-ai/DeepSeek-V4-Pro:novita",
                "messages",
                List.of(
                        Map.of(
                                "role","user",
                                "content",
                                """
                                Improve JavaDocs only.
                                Preserve logic.
                                Return only code.
           
                                """ + code
                        )
                )
        );

        String response = webClient.post()
                .uri("https://router.huggingface.co/v1/chat/completions")
                .header("Authorization","Bearer " + token)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode json = mapper.readTree(response);

        return json
                .get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
    }
}