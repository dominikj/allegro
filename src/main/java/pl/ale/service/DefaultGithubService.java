package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ale.dto.RepositoryDto;
import pl.ale.dto.UserDto;
import pl.ale.dto.UserListDto;
import pl.ale.enums.UserType;
import pl.ale.rest.response.RepositoryItem;
import pl.ale.rest.response.UserItem;
import pl.ale.rest.response.UserList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.ale.constant.Constants.GITHUB_MAX_SEARCH_RESULTS;
import static pl.ale.constant.Constants.githubUrls.*;

@Service
public class DefaultGithubService implements GithubService {

    private final RestTemplate restTemplate;


    @Autowired
    public DefaultGithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RepositoryDto> getRepositoriesForPersonalUser(String username) {
        return getRepositories(username, false);

    }

    @Override
    public List<RepositoryDto> getRepositoriesForOrganization(String username) {
        return getRepositories(username, true);
    }

    @Override
    public UserListDto searchUsers(String query) {

        ResponseEntity<UserList> response = restTemplate.getForEntity(SEARCH_USER_URL + query, UserList.class);

        UserListDto userList = new UserListDto();

        userList.setUsers(response.getBody().getItems().stream().map(this::buildUserDto).collect(Collectors.toList()));
        userList.setTooManyResults(response.getBody().getTotal_count() > GITHUB_MAX_SEARCH_RESULTS);

        return userList;
    }

    private List<RepositoryDto> getRepositories(String username, boolean isOrganization) {

        String url;
        if (isOrganization) {
            url = REPOS_ORG_URL;
        } else {
            url = REPOS_PERSONAL_USER_URL;
        }

        ResponseEntity<RepositoryItem[]> response =
                restTemplate.getForEntity(url.replace(USER_VAR, username), RepositoryItem[].class);

        return Arrays.stream(response.getBody()).map(this::buildRepositoryDto).collect(Collectors.toList());

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
        dto.setOwnerLogin(item.getOwner().getLogin());

        return dto;
    }
}
