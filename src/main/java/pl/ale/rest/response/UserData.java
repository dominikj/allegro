package pl.ale.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.ale.enums.UserType;

/**
 * Created by dominik on 02.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    private int public_repos;
    private String login;
    private UserType type;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = UserType.valueOf(type);
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }
}
