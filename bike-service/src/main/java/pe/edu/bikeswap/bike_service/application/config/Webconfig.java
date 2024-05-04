package pe.edu.bikeswap.bike_service.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Webconfig {
    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
}
