package pl.ale.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dominik on 01.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryItem {

    private String name;
    private int watchers_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }
}
