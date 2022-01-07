package pl.ale.dto;

import lombok.Data;

import java.util.Map;

@Data
public class RepositoryDto {

    private String name;
    private int stars;
    private String description;
    private Map<String, Integer> languages;
}
