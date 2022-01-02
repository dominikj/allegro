package pl.ale.dto;

/**
 * Created by dominik on 02.01.22.
 */
public class UserDto {
    private String login;
    private boolean isOrganization;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isOrganization() {
        return isOrganization;
    }

    public void setOrganization(boolean organization) {
        isOrganization = organization;
    }
}
