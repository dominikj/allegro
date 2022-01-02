package pl.ale.dto;

import java.util.List;

/**
 * Created by dominik on 02.01.22.
 */
public class UserListDto {

    private List<UserDto> users;
    private boolean tooManyResults;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }

    public boolean isTooManyResults() {
        return tooManyResults;
    }

    public void setTooManyResults(boolean results) {
        this.tooManyResults = results;
    }
}
