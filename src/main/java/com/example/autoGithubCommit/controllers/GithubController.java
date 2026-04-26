package com.example.autoGithubCommit.controllers;

import com.example.autoGithubCommit.services.GithubService;
import com.example.autoGithubCommit.services.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
public class GithubController {
    @Autowired
    LLMService llmService;
    private final GithubService githubService;

    @GetMapping("/readme")
    public String readme(
            @RequestParam String owner,
            @RequestParam String repo
    ) throws Exception {
        return githubService.fetchReadme(owner,repo);
    }

    @GetMapping("/auto-refactor")
    public String autoRefactor(
            @RequestParam String owner,
            @RequestParam String repo,
            @RequestParam String path
    ) throws Exception {

        String originalCode =
                githubService.fetchFile(
                        owner,
                        repo,
                        path
                );

        return llmService.improveCode(
                originalCode
        );
    }

    @GetMapping("/ai")
    public String ai() throws Exception {
        return llmService.improveCode(
                "public int add(int a,int b){return a+b;}"
        );
    }

}
