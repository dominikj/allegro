package pl.ale.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.ale.service.GithubService;

/**
 * Created by dominik on 01.01.22.
 */
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubService.class);

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.password}")
    private String githubPassword;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        if (isCredentialsFilled()) {
            return builder.basicAuthentication(githubUsername, githubPassword).build();

        }
        LOGGER.warn("Anonymus user can request github api 60 times per hour only");
        return builder.build();
    }

    private boolean isCredentialsFilled() {
        return githubUsername != null && !githubUsername.isEmpty() && githubPassword != null && !githubPassword.isEmpty();
    }
}
