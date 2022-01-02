package pl.ale.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dominik on 02.01.22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserItem {

    private String login;
    private String repos_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }
}
