package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ale.dto.UserDto;
import pl.ale.enums.UserType;
import pl.ale.rest.response.RepositoryItem;
import pl.ale.rest.response.UserItem;
import pl.ale.rest.response.UserList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.ale.constant.Constants.githubUrls.*;

@Service
public class DefaultGithubService implements GithubService {

    private final RestTemplate restTemplate;


    @Autowired
    public DefaultGithubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RepositoryItem> getRepositoriesForPersonalUser(String username) {
        return getRepositories(username, false);

    }

    @Override
    public List<RepositoryItem> getRepositoriesForOrganization(String username) {
        return getRepositories(username, true);
    }

    @Override
    public List<UserDto> searchUsers(String query) {

        ResponseEntity<UserList> response = restTemplate.getForEntity(SEARCH_USER_URL + query, UserList.class);

        return response.getBody().getItems().stream().map(this::buildUserDto).collect(Collectors.toList());
    }

    private List<RepositoryItem> getRepositories(String username, boolean isOrganization) {

        String url;
        if (isOrganization) {
            url = REPOS_ORG_URL;
        } else {
            url = REPOS_PERSONAL_USER_URL;
        }

        ResponseEntity<RepositoryItem[]> response =
                restTemplate.getForEntity(url.replace(USER_VAR, username), RepositoryItem[].class);

        return Arrays.asList(response.getBody());

    }

    private UserDto buildUserDto(UserItem item) {

        UserDto dto = new UserDto();

        dto.setLogin(item.getLogin());
        dto.setOrganization(UserType.Organization.equals(item.getType()));

        return dto;
    }
}
