package uz.agro.users.config;

import uz.agro.users.helpers.Constants;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class Config {
    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                    .serverUrl(Constants.SERVER_URL.getName())
                    .realm(Constants.MASTER.getName())
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(Constants.ADMIN.getName())
                    .password(Constants.ADMIN.getName())
                    .clientId(Constants.ADMIN_CLI.getName())
                    .resteasyClient(new ResteasyClientBuilderImpl()
                            .connectionPoolSize(10)
                            .build())
                    .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
