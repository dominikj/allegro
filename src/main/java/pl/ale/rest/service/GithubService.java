package pl.ale.rest.service;

import pl.ale.rest.response.RepositoryItem;
import pl.ale.rest.response.UserItem;

import java.util.List;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    List<RepositoryItem> getRepositoriesForPersonalUser(String username);
    List<RepositoryItem> getRepositoriesForOrganisation(String username);

    List<UserItem> searchUsers(String query);

}
