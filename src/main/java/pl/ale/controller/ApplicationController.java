package pl.ale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ale.dto.RepositoryListDto;
import pl.ale.service.GithubService;

/**
 * Created by dominik on 01.01.22.
 */
@RestController
public class ApplicationController {

    private final GithubService githubService;

    @Autowired
    public ApplicationController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/get-repos/{user}")
    public RepositoryListDto getRepos(@PathVariable String user,
                                      @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer page) {

        int reposPerPage = pageSize == null ? 30 : pageSize;
        int currentPage = page == null ? 1 : page;

        return githubService.getRepositories(user, reposPerPage, currentPage);
    }
}
