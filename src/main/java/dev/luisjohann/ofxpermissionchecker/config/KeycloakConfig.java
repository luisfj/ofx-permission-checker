package dev.luisjohann.ofxpermissionchecker.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

   @Value("${keycloak.urls.auth}")
   String serverUrl;
   @Value("${keycloak.realm}")
   String realm;
   @Value("${keycloak.adminClientId}")
   String clientId;
   @Value("${keycloak.adminClientSecret}")
   String clientSecret;

   @Bean
   Keycloak keycloak() {
      return KeycloakBuilder.builder()
            .serverUrl(serverUrl)
            .realm(realm)
            .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build();
   }
}