package pl.ale.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.ale.enums.UserType;

/**
 * Created by dominik on 02.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserItem {

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
}
