package pl.ale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ale.service.GithubService;

import static pl.ale.constant.Constants.GITHUB_MAX_QUERY_SIZE;

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

            String trimmedQuery = query.trim();

            if (trimmedQuery.isEmpty() || trimmedQuery.length() > GITHUB_MAX_QUERY_SIZE) {
                model.addAttribute("isEmptyOrTooLongQuery", true);

            } else {
                model.addAttribute("userList", githubService.searchUsers(trimmedQuery));
            }
        }
        return "searchPage";
    }

    @GetMapping("/get-repos/{user}")
    public String getRepos(@PathVariable String user, @RequestParam boolean isOrganization, Model model) {

        if (user != null || !user.isEmpty()) {

            if (isOrganization) {
                model.addAttribute("repos", githubService.getRepositoriesForOrganization(user));

            } else {
                model.addAttribute("repos", githubService.getRepositoriesForPersonalUser(user));
            }
        }

        return "reposListPage";
    }
}
