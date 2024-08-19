package dev.luisjohann.ofxpermissionchecker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
      @Bean
      public SecurityFilterChain securityWebFilterChain(HttpSecurity httpSecurity) throws Exception {

            var http = httpSecurity
                        .csrf(CsrfConfigurer::disable)
                        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                    .requestMatchers("/public/**")
                                    .permitAll()
                                    .anyRequest().authenticated())
                        .oauth2ResourceServer((oauth) -> oauth
                                    .jwt(Customizer.withDefaults()));

            return http.build();
      }

}
