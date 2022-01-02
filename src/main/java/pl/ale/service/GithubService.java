package pl.ale.service;

import pl.ale.dto.RepositoryDto;
import pl.ale.dto.UserListDto;

import java.util.List;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    List<RepositoryDto> getRepositoriesForPersonalUser(String username);

    List<RepositoryDto> getRepositoriesForOrganization(String username);

    UserListDto searchUsers(String query);

}
