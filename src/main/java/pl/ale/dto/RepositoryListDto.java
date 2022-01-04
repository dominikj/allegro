package pl.ale.dto;

import java.util.List;

/**
 * Created by dominik on 02.01.22.
 */
public class RepositoryListDto {

    private int currentPage;
    private int numberOfPages;
    private String user;
    private int totalNumberOfStars;

    private List<RepositoryDto> repositories;

    public int getTotalNumberOfStars() {
        return totalNumberOfStars;
    }

    public void setTotalNumberOfStars(int totalNumberOfStars) {
        this.totalNumberOfStars = totalNumberOfStars;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public List<RepositoryDto> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDto> repositories) {
        this.repositories = repositories;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

}
