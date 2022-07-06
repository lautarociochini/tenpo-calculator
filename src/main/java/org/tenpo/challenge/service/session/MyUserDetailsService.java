package org.tenpo.challenge.service.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tenpo.challenge.enums.Role;
import org.tenpo.challenge.model.internal.User;
import org.tenpo.challenge.service.user.UserService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findUserByUsername(username);
        if (user == null) {
            String message = String.format("User %s not found", username);
            logger.info(message);
            throw new UsernameNotFoundException(message);
        }

        List<GrantedAuthority> authorities = getUserAuthorities();
        return getUserDetailsForAuth(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.toString()));
    }

    private UserDetails getUserDetailsForAuth(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
