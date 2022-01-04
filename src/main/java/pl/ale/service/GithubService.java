package pl.ale.service;

import pl.ale.dto.RepositoryListDto;
import pl.ale.dto.UserListDto;

import java.util.Map;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    RepositoryListDto getRepositoriesForPersonalUser(String username, int page);

    RepositoryListDto getRepositoriesForOrganization(String username, int page);

    int getTotalNumberOfStars(String username);

    Map<String, Long> getRepositoryLanguages(String username, String repository);

    UserListDto searchUsers(String query);

}
