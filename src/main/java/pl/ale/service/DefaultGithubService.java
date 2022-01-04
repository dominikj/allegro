package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ale.dto.RepositoryDto;
import pl.ale.dto.RepositoryListDto;
import pl.ale.enums.UserType;
import pl.ale.rest.response.RepositoryItem;
import pl.ale.rest.response.UserData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.ale.constant.Constants.GITHUB_MAX_RESULTS_PER_PAGE;
import static pl.ale.constant.Constants.githubUrls.*;

@Service
public class DefaultGithubService implements GithubService {

    private final RestTemplate restTemplate;


    @Autowired
    public DefaultGithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    @Cacheable(value = "numberOfStars")
    public int getTotalNumberOfStars(UserData userData) {

        int numberOfPages = calculateNumberOfPages(userData.getPublic_repos(), GITHUB_MAX_RESULTS_PER_PAGE);

        int totalStars = 0;

        for (int page = 1; page <= numberOfPages; ++page) {
            RepositoryItem[] repositories = getRepositories(userData.getLogin(), isOgranization(userData),
                    GITHUB_MAX_RESULTS_PER_PAGE, page);

            int sumOfStars = Arrays.stream(repositories).mapToInt(RepositoryItem::getStargazers_count).sum();

            totalStars += sumOfStars;
        }

        return totalStars;
    }

    @Override
    public Map<String, Integer> getRepositoryLanguages(String username, String repository) {
        return restTemplate.getForEntity(String.format(REPO_LANGUAGES_URL, username, repository), HashMap.class).getBody();
    }

    private UserData getUserData(String username) {

        return restTemplate.getForEntity(String.format(USER_DATA_URL, username), UserData.class).getBody();

    }

    @Override
    public RepositoryListDto getRepositories(String username, int pageSize, int page) {

        UserData userData = getUserData(username);
        int totalNumberOfStars = getTotalNumberOfStars(userData);

        RepositoryItem[] repositories = getRepositories(username, isOgranization(userData), pageSize, page);

        return buildRepositoryListDto(repositories, userData, pageSize, page, totalNumberOfStars);

    }

    private RepositoryItem[] getRepositories(String username, boolean isOrganization, int pageSize, int page) {

        String url;
        if (isOrganization) {

            url = String.format(REPOS_ORG_URL, username, pageSize, page);

        } else {

            url = String.format(REPOS_PERSONAL_USER_URL, username, pageSize, page);
        }

        return restTemplate.getForEntity(url, RepositoryItem[].class).getBody();
    }

    private RepositoryListDto buildRepositoryListDto(RepositoryItem[] repositories, UserData userData,
                                                     int pageSize, int page, int totalNumberOfStars) {

        RepositoryListDto repositoryList = new RepositoryListDto();

        repositoryList.setRepositories(Arrays.stream(repositories)
                .map(repo -> buildRepositoryDto(repo, userData.getLogin()))
                .collect(Collectors.toList()));

        repositoryList.setUser(userData.getLogin());
        repositoryList.setCurrentPage(page);
        repositoryList.setNumberOfPages(calculateNumberOfPages(userData.getPublic_repos(), pageSize));
        repositoryList.setTotalNumberOfStars(totalNumberOfStars);

        return repositoryList;

    }

    private RepositoryDto buildRepositoryDto(RepositoryItem item, String username) {

        RepositoryDto dto = new RepositoryDto();

        dto.setDescription(item.getDescription());
        dto.setName(item.getName());
        dto.setStars(item.getStargazers_count());
        dto.setLanguages(getRepositoryLanguages(username, dto.getName()));

        return dto;
    }

    private boolean isOgranization(UserData user) {

        return UserType.Organization.equals(user.getType());
    }

    private int calculateNumberOfPages(int numberOfRepos, int pageSize) {

        return (int) Math.ceil((double) numberOfRepos / pageSize);
    }
}
