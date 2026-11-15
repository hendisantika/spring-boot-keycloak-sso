package id.my.hendisantika.professorapp.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
class KeycloakAuthoritiesMapperTest {

    private final KeycloakAuthoritiesMapper mapper = new KeycloakAuthoritiesMapper("professor-app");

    @Test
    void mapsRealmAndClientRolesToAuthorities() {
        OidcIdToken idToken = new OidcIdToken("token", Instant.now(), Instant.now().plusSeconds(60), Map.of(
                "sub", "user-1",
                "realm_access", Map.of("roles", List.of("PROFESSOR")),
                "resource_access", Map.of(
                        "professor-app", Map.of("roles", List.of("STUDENT")),
                        "other-app", Map.of("roles", List.of("ADMIN")))));

        Set<String> authorities = mapper.mapAuthorities(List.of(new OidcUserAuthority(idToken))).stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertThat(authorities).contains("PROFESSOR", "STUDENT").doesNotContain("ADMIN");
    }

    @Test
    void ignoresTokensWithoutRoles() {
        OidcIdToken idToken = new OidcIdToken("token", Instant.now(), Instant.now().plusSeconds(60),
                Map.of("sub", "user-1"));

        assertThat(mapper.extractRoles(idToken.getClaims())).isEmpty();
    }
}
