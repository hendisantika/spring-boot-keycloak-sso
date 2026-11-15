package id.my.hendisantika.studentapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sso
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 09/10/24
 * Time: 11.43
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class KeycloakSecurityConfig {

    /**
     * Single Sign-On through Keycloak using standard OpenID Connect (Spring Security OAuth2 Client).
     * Replaces the deprecated / removed Keycloak Spring adapter.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   LogoutSuccessHandler keycloakLogoutSuccessHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/contact-us", "/access-denied", "/error").permitAll()  // Public pages
                        .anyRequest().authenticated())                                            // Everything else needs login
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/home", true))
                .logout(logout -> logout.logoutSuccessHandler(keycloakLogoutSuccessHandler))
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));

        return http.build();
    }

    /**
     * Maps Keycloak realm roles and client roles (e.g. {@code PROFESSOR}, {@code STUDENT}) to Spring Security authorities.
     */
    @Bean
    public GrantedAuthoritiesMapper keycloakAuthoritiesMapper(
            @Value("${spring.security.oauth2.client.registration.keycloak.client-id}") String clientId) {
        return new KeycloakAuthoritiesMapper(clientId);
    }

    /**
     * Logs the user out from Keycloak too (RP-Initiated Logout), so the SSO session is terminated.
     */
    @Bean
    public LogoutSuccessHandler keycloakLogoutSuccessHandler(@Value("${keycloak.base-url}") String keycloakBaseUrl,
                                                             @Value("${keycloak.realm}") String realm) {
        return new KeycloakLogoutSuccessHandler(keycloakBaseUrl, realm, "/contact-us");
    }
}
