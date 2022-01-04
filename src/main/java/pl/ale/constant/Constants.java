package pl.ale.constant;

/**
 * Created by dominik on 02.01.22.
 */
public interface Constants {

    int GITHUB_MAX_RESULTS_PER_PAGE = 100;
    int GITHUB_DEFAULT_PAGE_SIZE = 30;
    int GTIHUB_DEFAULT_PAGE = 1;
    int MINIMAL_REPOS_PER_PAGE = 1;


    interface githubUrls {

        String PAGE = "&page=%d";

        String REPOS_PERSONAL_USER_URL = "https://api.github.com/users/%s/repos?per_page=%d" + PAGE;
        String REPOS_ORG_URL = "https://api.github.com/orgs/%s/repos?per_page=%d" + PAGE;
        String USER_DATA_URL = "https://api.github.com/users/%s";
        String REPO_LANGUAGES_URL = "https://api.github.com/repos/%s/%s/languages";

    }
}
