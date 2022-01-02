package pl.ale.service;

import pl.ale.dto.RepositoryDto;

import java.util.List;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    List<RepositoryDto> getRepositoriesForUser(String username);
}
