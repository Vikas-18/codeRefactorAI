package com.example.autoGithubCommit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final WebClient webClient;

    @Value("${github.token}")
    private String token;

    private final ObjectMapper objectMapper
            = new ObjectMapper();

    public String fetchReadme(String owner,String repo)
            throws Exception {

        String url =
                "https://api.github.com/repos/"
                        + owner + "/" + repo
                        + "/contents/README.md";

        String response = webClient.get()
                .uri(url)
                .header("Authorization",
                        "Bearer "+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode json=
                objectMapper.readTree(response);

        String encoded=
                json.get("content")
                        .asText()
                        .replace("\n","");

        byte[] decoded=
                Base64.getDecoder()
                        .decode(encoded);

        return new String(decoded);
    }

    public String fetchFile(
            String owner,
            String repo,
            String path
    ) throws Exception {

        String url =
                "https://api.github.com/repos/"
                        + owner + "/" + repo
                        + "/contents/" + path;

        String response = webClient.get()
                .uri(url)
                .header("Authorization","Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode json = objectMapper.readTree(response);

        String encoded = json.get("content")
                .asText()
                .replace("\n","");

        byte[] decoded =
                Base64.getDecoder()
                        .decode(encoded);

        return new String(decoded);
    }
}