package pl.ale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ale.rest.service.GithubService;

/**
 * Created by dominik on 01.01.22.
 */
@Controller
public class ApplicationController {

    private final GithubService githubService;

    @Autowired
    public ApplicationController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/")
    public String searchUsers(@RequestParam(required = false) String query, Model model) {

        if (query != null) {
            model.addAttribute("userList", githubService.searchUsers(query));
        }
        return "searchPage";
    }

    @GetMapping("/get-repos")
    public String getRepos(@RequestParam String user, Model model) {

        model.addAttribute("repos", githubService.getRepositoriesForPersonalUser(user));

        return "reposList";
    }
}
