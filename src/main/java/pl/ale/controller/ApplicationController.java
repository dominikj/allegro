package pl.ale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ale.dto.RepositoryListDto;
import pl.ale.service.GithubRepositoriesService;

import static pl.ale.constant.Constants.*;

@RestController
public class ApplicationController {

    private final GithubRepositoriesService githubRepositoriesService;

    @Autowired
    public ApplicationController(GithubRepositoriesService githubRepositoriesService) {
        this.githubRepositoriesService = githubRepositoriesService;
    }

    @GetMapping("/get-repos/{user}")
    public RepositoryListDto getRepos(@PathVariable String user,
                                      @RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) Integer page) {

        int reposPerPage = pageSize == null ? GITHUB_DEFAULT_RESULTS_PER_PAGE : pageSize;
        reposPerPage = (reposPerPage < MINIMAL_REPOS_PER_PAGE || reposPerPage > GITHUB_MAX_RESULTS_PER_PAGE)
                ? GITHUB_DEFAULT_RESULTS_PER_PAGE : reposPerPage;

        int currentPage = page == null ? GTIHUB_DEFAULT_PAGE : page;

        return githubRepositoriesService.getRepositories(user, reposPerPage, currentPage);
    }
}
