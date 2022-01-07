package pl.ale.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryItem {

    private String name;
    private int stargazers_count;
    private String description;
}
