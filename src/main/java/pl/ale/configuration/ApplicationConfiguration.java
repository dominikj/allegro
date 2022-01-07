package pl.ale.configuration;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class ApplicationConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Value("${github.personalToken}")
    private String personalToken;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        if (!Strings.isNullOrEmpty(personalToken)) {
            return builder.defaultHeader("Authorization", "token " + personalToken).build();
        }

        LOGGER.warn("Anonymus user has lower rate limit");
        return builder.build();
    }
}
