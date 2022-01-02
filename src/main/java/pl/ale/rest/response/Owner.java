package pl.ale.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dominik on 02.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
