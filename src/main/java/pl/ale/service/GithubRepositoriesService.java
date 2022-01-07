package pl.ale.service;

import pl.ale.dto.RepositoryListDto;
import pl.ale.param.Pagination;

/**
 * Service for obtaining github repos.
 */
public interface GithubRepositoriesService {

    /**
     * Gets repos for given github user and some additional information as:
     * - number of stars for each repo
     * - sum of stars for all repos
     * - code languages and code size for each repo
     * - description for each repo
     *
     * @param username github username
     * @param pageable pagination
     * @return The list of repos is wrapped in {@link RepositoryListDto}
     */
    RepositoryListDto getRepositories(String username, Pagination pageable);

}
