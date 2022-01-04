package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ale.enums.UserType;
import pl.ale.response.RepositoryItem;
import pl.ale.response.UserData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static pl.ale.constant.Constants.GITHUB_MAX_RESULTS_PER_PAGE;
import static pl.ale.constant.Constants.githubUrls.*;
import static pl.ale.util.Util.calculateNumberOfPages;

@Service
public class DefaultGithubService implements GithubService {

    private final RestTemplate restTemplate;


    @Autowired
    public DefaultGithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    @Cacheable(value = "totalStars", key = "#userData.login") //TODO: cache has no eviction policy
    public int getTotalNumberOfStars(UserData userData) {

        int numberOfPages = calculateNumberOfPages(userData.getPublic_repos(), GITHUB_MAX_RESULTS_PER_PAGE);

        int totalStars = 0;

        for (int page = 1; page <= numberOfPages; ++page) {
            RepositoryItem[] repositories = getRepositories(userData, GITHUB_MAX_RESULTS_PER_PAGE, page);

            int sumOfStars = Arrays.stream(repositories).mapToInt(RepositoryItem::getStargazers_count).sum();

            totalStars += sumOfStars;
        }

        return totalStars;
    }

    @Override
    public Map<String, Integer> getRepositoryLanguages(String username, String repository) {
        return restTemplate.getForEntity(String.format(REPO_LANGUAGES_URL, username, repository), HashMap.class).getBody();
    }

    @Override
    public UserData getUserData(String username) {

        return restTemplate.getForEntity(String.format(USER_DATA_URL, username), UserData.class).getBody();

    }

    @Override
    public RepositoryItem[] getRepositories(UserData userData, int pageSize, int page) {

        String url;
        if (isOgranization(userData)) {

            url = String.format(REPOS_ORG_URL, userData.getLogin(), pageSize, page);

        } else {

            url = String.format(REPOS_PERSONAL_USER_URL, userData.getLogin(), pageSize, page);
        }

        return restTemplate.getForEntity(url, RepositoryItem[].class).getBody();
    }

    private boolean isOgranization(UserData user) {

        return UserType.Organization.equals(user.getType());
    }
}
