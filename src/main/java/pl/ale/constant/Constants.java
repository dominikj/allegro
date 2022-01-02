package pl.ale.constant;

/**
 * Created by dominik on 02.01.22.
 */
public interface Constants {

    interface githubUrls {
        String USER_VAR = "${user}";
        String REPOS_ORG_URL = "https://api.github.com/orgs/" + USER_VAR + "/repos";
        String REPOS_PERSONAL_USER_URL = "https://api.github.com/users/" + USER_VAR + "/repos";

        String SEARCH_USER_URL = "https://api.github.com/search/users?q=";
    }
}
