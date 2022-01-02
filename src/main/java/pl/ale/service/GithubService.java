package pl.ale.service;

import pl.ale.dto.UserDto;
import pl.ale.rest.response.RepositoryItem;

import java.util.List;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    List<RepositoryItem> getRepositoriesForPersonalUser(String username);

    List<RepositoryItem> getRepositoriesForOrganization(String username);

    List<UserDto> searchUsers(String query);

}
