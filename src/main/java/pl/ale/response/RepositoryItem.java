package pl.ale.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dominik on 01.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryItem {

    private String name;
    private int stargazers_count;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }
}
