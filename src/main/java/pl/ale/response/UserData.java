package pl.ale.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import pl.ale.enums.UserType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {

    private int public_repos;
    private String login;
    private UserType type;
}
