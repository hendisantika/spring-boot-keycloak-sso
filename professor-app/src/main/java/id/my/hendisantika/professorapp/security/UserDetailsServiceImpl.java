package id.my.hendisantika.professorapp.security;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-keycloak-sso
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 09/10/24
 * Time: 11.35
 * To change this template use File | Settings | File Templates.
 */

import id.my.hendisantika.professorapp.entity.Roles;
import id.my.hendisantika.professorapp.entity.User;
import id.my.hendisantika.professorapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (Roles role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), authorities);

    }
}
