package pl.ale.service;

import org.springframework.stereotype.Service;
import pl.ale.dto.RepositoryDto;

import java.util.List;

@Service
public class DefaultGithubService implements GithubService {
    @Override
    public List<RepositoryDto> getRepositoriesForUser(String username) {
        return null;
    }
}
