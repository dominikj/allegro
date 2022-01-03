package pl.ale.service;

import pl.ale.dto.RepositoryListDto;
import pl.ale.dto.UserListDto;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    RepositoryListDto getRepositoriesForPersonalUser(String username, int page);

    RepositoryListDto getRepositoriesForOrganization(String username, int page);

    UserListDto searchUsers(String query);

}
