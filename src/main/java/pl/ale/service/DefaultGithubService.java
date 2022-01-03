package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
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
        return getRepositories(username, false, page);

    }

    @Override
    public RepositoryListDto getRepositoriesForOrganization(String username, int page) {
        return getRepositories(username, true, page);
    }

    @Override
    public UserListDto searchUsers(String query) {

        ResponseEntity<UserList> response = restTemplate.getForEntity(SEARCH_USER_URL + query, UserList.class);

        UserListDto userList = new UserListDto();

        userList.setUsers(response.getBody().getItems().stream().map(this::buildUserDto).collect(Collectors.toList()));
        userList.setTooManyResults(response.getBody().getTotal_count() > GITHUB_MAX_SEARCH_RESULTS_PER_PAGE);

        return userList;
    }

    private UserData getUserData(String username) {

        return restTemplate.getForEntity(String.format(GET_USER_DATA_URL, username), UserData.class).getBody();

    }

    private RepositoryListDto getRepositories(String username, boolean isOrganization, int page) {

        String url;
        if (isOrganization) {

            url = String.format(REPOS_ORG_URL, username, GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE, page);

        } else {

            url = String.format(REPOS_PERSONAL_USER_URL, username, GITHUB_DEFAULT_SEARCH_RESULTS_PER_PAGE, page);

        }

        ResponseEntity<RepositoryItem[]> repositories = restTemplate.getForEntity(url, RepositoryItem[].class);

        UserData userData = getUserData(username);

        return buildRepositoryListDto(repositories.getBody(), userData, page);

    }

    private RepositoryListDto buildRepositoryListDto(RepositoryItem[] repositories, UserData userData, int page) {

        RepositoryListDto repositoryList = new RepositoryListDto();

        repositoryList.setRepositories(Arrays.stream(repositories).map(this::buildRepositoryDto).collect(Collectors.toList()));
        repositoryList.setLogin(userData.getLogin());
        repositoryList.setOrganization(UserType.Organization.equals(userData.getType()));
        repositoryList.setCurrentPage(page);
        repositoryList.setNumberOfPages(calculateNumberOfPages(userData.getPublic_repos()));

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

    private RepositoryDto buildRepositoryDto(RepositoryItem item) {

        RepositoryDto dto = new RepositoryDto();

        dto.setDescription(item.getDescription());
        dto.setName(item.getName());
        dto.setStars(item.getWatchers_count());

        return dto;
    }
}
