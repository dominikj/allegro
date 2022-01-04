package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ale.dto.RepositoryDto;
import pl.ale.dto.RepositoryListDto;
import pl.ale.dto.UserDto;
import pl.ale.dto.UserListDto;
import pl.ale.enums.UserType;
import pl.ale.rest.response.RepositoryItem;
import pl.ale.rest.response.UserData;
import pl.ale.rest.response.UserItem;
import pl.ale.rest.response.UserList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.ale.constant.Constants.GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE;
import static pl.ale.constant.Constants.GITHUB_MAX_SEARCH_RESULTS_PER_PAGE;
import static pl.ale.constant.Constants.githubUrls.*;

@Service
public class DefaultGithubService implements GithubService {

    private final RestTemplate restTemplate;


    @Autowired
    public DefaultGithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RepositoryListDto getRepositoriesForPersonalUser(String username, int page) {
        return getRepositoryList(username, false, page);

    }

    @Override
    public RepositoryListDto getRepositoriesForOrganization(String username, int page) {
        return getRepositoryList(username, true, page);
    }

    @Override
    public UserListDto searchUsers(String query) {

        ResponseEntity<UserList> response = restTemplate.getForEntity(SEARCH_USER_URL + query, UserList.class);

        UserListDto userList = new UserListDto();

        userList.setUsers(response.getBody().getItems().stream().map(this::buildUserDto).collect(Collectors.toList()));
        userList.setTooManyResults(response.getBody().getTotal_count() > GITHUB_MAX_SEARCH_RESULTS_PER_PAGE);

        return userList;
    }

    @Override
    @Cacheable(value = "numberOfStars")
    public int getTotalNumberOfStars(String username) {

        UserData userData = getUserData(username);

        int numberOfPages = calculateNumberOfPages(userData.getPublic_repos());

        int totalStars = 0;

        for (int page = 1; page <= numberOfPages; ++page) {
            RepositoryItem[] repositories = getRepositories(username, UserType.Organization.equals(userData.getType()), page);

            int sumOfStars = Arrays.stream(repositories).mapToInt(RepositoryItem::getStargazers_count).sum();

            totalStars += sumOfStars;
        }

        return totalStars;
    }

    @Override
    public Map<String, Long> getRepositoryLanguages(String username, String repository) {
        return restTemplate.getForEntity(String.format(GET_REPO_LANGUAGES, username, repository), HashMap.class).getBody();
    }

    private UserData getUserData(String username) {

        return restTemplate.getForEntity(String.format(GET_USER_DATA_URL, username), UserData.class).getBody();

    }

    private RepositoryListDto getRepositoryList(String username, boolean isOrganization, int page) {

        int totalNumberOfStars = getTotalNumberOfStars(username);

        RepositoryItem[] repositories = getRepositories(username, isOrganization, page);

        UserData userData = getUserData(username);

        return buildRepositoryListDto(repositories, userData, page, totalNumberOfStars);

    }

    private RepositoryItem[] getRepositories(String username, boolean isOrganization, int page) {

        String url;
        if (isOrganization) {

            url = String.format(REPOS_ORG_URL, username, GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE, page);

        } else {

            url = String.format(REPOS_PERSONAL_USER_URL, username, GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE, page);

        }

        return restTemplate.getForEntity(url, RepositoryItem[].class).getBody();
    }

    private RepositoryListDto buildRepositoryListDto(RepositoryItem[] repositories, UserData userData, int page, int totalNumberOfStars) {

        RepositoryListDto repositoryList = new RepositoryListDto();

        repositoryList.setRepositories(Arrays.stream(repositories)
                .map(repo -> buildRepositoryDto(repo, userData.getLogin()))
                .collect(Collectors.toList()));

        repositoryList.setLogin(userData.getLogin());
        repositoryList.setOrganization(UserType.Organization.equals(userData.getType()));
        repositoryList.setCurrentPage(page);
        repositoryList.setNumberOfPages(calculateNumberOfPages(userData.getPublic_repos()));
        repositoryList.setTotalNumberOfStars(totalNumberOfStars);

        return repositoryList;

    }

    private int calculateNumberOfPages(int numberOfRepos) {

        return (int) Math.ceil((double) numberOfRepos / GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE);
    }

    private UserDto buildUserDto(UserItem item) {

        UserDto dto = new UserDto();

        dto.setLogin(item.getLogin());
        dto.setOrganization(UserType.Organization.equals(item.getType()));

        return dto;
    }

    private RepositoryDto buildRepositoryDto(RepositoryItem item, String username) {

        RepositoryDto dto = new RepositoryDto();

        dto.setDescription(item.getDescription());
        dto.setName(item.getName());
        dto.setStars(item.getStargazers_count());
        dto.setLanguages(getRepositoryLanguages(username, dto.getName()));
        return dto;
    }
}
