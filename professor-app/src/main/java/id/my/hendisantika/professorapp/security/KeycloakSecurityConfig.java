package id.my.hendisantika.professorapp.security;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sso
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 09/10/24
 * Time: 11.28
 * To change this template use File | Settings | File Templates.
 */

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@KeycloakConfiguration
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    KeycloakSecurityConfig extends

    KeycloakWebSecurityConfigurerAdapter {
        // This is to use Keycloak as the authentication provider
        @Autowired
        public void configureGlobal (AuthenticationManagerBuilder auth){
            auth.authenticationProvider(keycloakAuthenticationProvider());
        }

    }
