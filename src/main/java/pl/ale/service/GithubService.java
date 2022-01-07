package pl.ale.service;

import pl.ale.param.Pagination;
import pl.ale.response.RepositoryItem;
import pl.ale.response.UserData;

import java.util.Map;

/**
 * Service for communication with github api.
 */
public interface GithubService {

    /**
     * Gets github repos.
     *
     * @param userData user data obtained from {@link #getUserData(String)}
     * @param pageable pagination
     * @return Repositories achieved from github api
     */
    RepositoryItem[] getRepositories(UserData userData, Pagination pageable);

    /**
     * Sums all stars for all repos for given github user
     *
     * @param userData user data obtained from {@link #getUserData(String)}
     * @return Sum of all stars. This value is cached per user for reducing number of requests to github api
     */
    int getTotalNumberOfStars(UserData userData);

    /**
     * Gets code languages and number of bytes written in each language for given repo
     *
     * @param username   github username
     * @param repository repository name
     * @return Map contains pairs: [language]:[number of bytes]
     */
    Map<String, Integer> getRepositoryLanguages(String username, String repository);

    /**
     * Gets github user data:
     * - login
     * - user type ( organization or personal user - it is useful information for building further requests)
     * - number of all public repos
     *
     * @param username github username
     * @return user data
     */
    UserData getUserData(String username);
}
