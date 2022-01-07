package pl.ale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.ale.dto.RepositoryListDto;
import pl.ale.param.Pagination;
import pl.ale.service.GithubRepositoriesService;

import javax.validation.Valid;

@RestController
public class ApplicationController {

    private final GithubRepositoriesService githubRepositoriesService;

    @Autowired
    public ApplicationController(GithubRepositoriesService githubRepositoriesService) {
        this.githubRepositoriesService = githubRepositoriesService;
    }

    @GetMapping("/repos/{user}")
    public RepositoryListDto getRepos(@PathVariable String user, @Valid Pagination pageable) {

        return githubRepositoriesService.getRepositories(user, pageable);
    }
}
