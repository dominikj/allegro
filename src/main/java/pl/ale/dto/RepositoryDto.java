package pl.ale.dto;

import java.util.Map;

/**
 * Created by dominik on 02.01.22.
 */
public class RepositoryDto {

    private String name;
    private int stars;
    private String description;
    private Map<String, Long> languages;

    public Map<String, Long> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, Long> languages) {
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
