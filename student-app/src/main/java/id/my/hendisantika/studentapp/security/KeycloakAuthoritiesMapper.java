package id.my.hendisantika.studentapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Project : student-app
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Link : s.id/hendisantika
 * Date: 15/11/26
 * Time: 17.26
 */
public class KeycloakAuthoritiesMapper implements GrantedAuthoritiesMapper {

    private final String clientId;

    public KeycloakAuthoritiesMapper(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> mapped = new HashSet<>(authorities);
        for (GrantedAuthority authority : authorities) {
            if (authority instanceof OidcUserAuthority oidcAuthority) {
                mapped.addAll(extractRoles(oidcAuthority.getIdToken().getClaims()));
                if (oidcAuthority.getUserInfo() != null) {
                    mapped.addAll(extractRoles(oidcAuthority.getUserInfo().getClaims()));
                }
            }
        }
        return mapped;
    }

    Set<GrantedAuthority> extractRoles(Map<String, Object> claims) {
        Set<GrantedAuthority> roles = new HashSet<>();
        // Realm roles: "realm_access": { "roles": [...] }
        if (claims.get("realm_access") instanceof Map<?, ?> realmAccess) {
            addRoles(realmAccess.get("roles"), roles);
        }
        // Client roles: "resource_access": { "<client-id>": { "roles": [...] } }
        if (claims.get("resource_access") instanceof Map<?, ?> resourceAccess
                && resourceAccess.get(clientId) instanceof Map<?, ?> clientAccess) {
            addRoles(clientAccess.get("roles"), roles);
        }
        return roles;
    }

    private static void addRoles(Object roles, Set<GrantedAuthority> target) {
        if (roles instanceof Collection<?> values) {
            values.forEach(role -> target.add(new SimpleGrantedAuthority(String.valueOf(role))));
        }
    }
}
