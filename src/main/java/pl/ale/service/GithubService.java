package pl.ale.service;

import pl.ale.dto.RepositoryDto;
import pl.ale.dto.UserDto;

import java.util.List;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    List<RepositoryDto> getRepositoriesForPersonalUser(String username);

    List<RepositoryDto> getRepositoriesForOrganization(String username);

    List<UserDto> searchUsers(String query);

}
