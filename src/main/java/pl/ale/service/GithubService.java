package pl.ale.service;

import pl.ale.dto.RepositoryListDto;
import pl.ale.rest.response.UserData;

import java.util.Map;

/**
 * Created by dominik on 01.01.22.
 */

public interface GithubService {

    RepositoryListDto getRepositories(String username, int pageSize, int page);

     int getTotalNumberOfStars(UserData userData);

    Map<String, Integer> getRepositoryLanguages(String username, String repository);

}
