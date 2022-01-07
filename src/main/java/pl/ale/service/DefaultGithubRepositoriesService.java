package pl.ale.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ale.dto.RepositoryDto;
import pl.ale.dto.RepositoryListDto;
import pl.ale.param.Pagination;
import pl.ale.response.RepositoryItem;
import pl.ale.response.UserData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static pl.ale.util.Util.calculateNumberOfPages;


@Service
public class DefaultGithubRepositoriesService implements GithubRepositoriesService {

    private final GithubService githubService;

    @Autowired
    public DefaultGithubRepositoriesService(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public RepositoryListDto getRepositories(String username, Pagination pageable) {

        UserData userData = githubService.getUserData(username);
        int totalNumberOfStars = githubService.getTotalNumberOfStars(userData);
        RepositoryItem[] repositories = githubService.getRepositories(userData, pageable);

        return buildRepositoryListDto(repositories, userData,
                pageable.getPageSize(), pageable.getPageNumber(), totalNumberOfStars);
    }

    private RepositoryListDto buildRepositoryListDto(RepositoryItem[] repositories, UserData userData,
                                                     int pageSize, int page, int totalNumberOfStars) {

        RepositoryListDto repositoryList = new RepositoryListDto();

        repositoryList.setRepositories(Arrays.stream(repositories)
                .map(repo -> buildRepositoryDto(repo, userData.getLogin()))
                .collect(Collectors.toList()));

        repositoryList.setUser(userData.getLogin());
        repositoryList.setCurrentPage(page);
        repositoryList.setNumberOfPages(calculateNumberOfPages(userData.getPublic_repos(), pageSize));
        repositoryList.setTotalNumberOfStars(totalNumberOfStars);

        return repositoryList;

    }

    private RepositoryDto buildRepositoryDto(RepositoryItem item, String username) {

        RepositoryDto dto = new RepositoryDto();

        dto.setDescription(item.getDescription());
        dto.setName(item.getName());
        dto.setStars(item.getStargazers_count());
        dto.setLanguages(githubService.getRepositoryLanguages(username, dto.getName()));

        return dto;
    }

}
