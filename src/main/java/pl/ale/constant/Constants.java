package pl.ale.constant;

/**
 * Created by dominik on 02.01.22.
 */
public interface Constants {

    int GITHUB_MAX_SEARCH_RESULTS_PER_PAGE = 100;
    int GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE = 100;

    int GITHUB_MAX_QUERY_SIZE = 256;

    interface githubUrls {

        String PAGE = "&page=%d";

        String REPOS_PERSONAL_USER_URL = "https://api.github.com/users/%s/repos?per_page=%d" + PAGE;
        String REPOS_ORG_URL = "https://api.github.com/orgs/%s/repos?per_page=%d" + PAGE;

        String SEARCH_USER_URL = "https://api.github.com/search/users?per_page=" + GITHUB_MAX_SEARCH_RESULTS_PER_PAGE + "&q=";
        String GET_USER_DATA_URL = "https://api.github.com/users/%s";

    }
}
