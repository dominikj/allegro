package pl.ale.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by dominik on 02.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList {

    private long total_count;
    private List<UserItem> items;

    public long getTotal_count() {
        return total_count;
    }

    public void setTotal_count(long total_count) {
        this.total_count = total_count;
    }

    public List<UserItem> getItems() {
        return items;
    }

    public void setItems(List<UserItem> items) {
        this.items = items;
    }
}
