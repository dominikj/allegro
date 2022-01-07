package pl.ale.dto;

import lombok.Data;

import java.util.List;

@Data
public class RepositoryListDto {

    private int currentPage;
    private int numberOfPages;
    private String user;
    private int totalNumberOfStars;

    private List<RepositoryDto> repositories;
}
