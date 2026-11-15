package id.my.hendisantika.professorapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * Project : professor-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 15/11/26
 * Time: 17.26
 */
public class KeycloakLogoutSuccessHandler implements LogoutSuccessHandler {

    private final String keycloakBaseUrl;
    private final String realm;
    private final String postLogoutPath;

    public KeycloakLogoutSuccessHandler(String keycloakBaseUrl, String realm, String postLogoutPath) {
        this.keycloakBaseUrl = keycloakBaseUrl;
        this.realm = realm;
        this.postLogoutPath = postLogoutPath;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        String postLogoutRedirectUri = ServletUriComponentsBuilder.fromContextPath(request)
                .path(postLogoutPath)
                .toUriString();

        UriComponentsBuilder logoutUri = UriComponentsBuilder.fromUriString(keycloakBaseUrl)
                .path("/realms/{realm}/protocol/openid-connect/logout")
                .queryParam("post_logout_redirect_uri", postLogoutRedirectUri);

        if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
            logoutUri.queryParam("id_token_hint", oidcUser.getIdToken().getTokenValue());
        }

        response.sendRedirect(logoutUri.buildAndExpand(realm).encode().toUriString());
    }
}
