package org.tenpo.challenge.service.session;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.tenpo.challenge.PostgreSQLMock;
import org.tenpo.challenge.model.dao.UserDAO;
import org.tenpo.challenge.repository.UserRepository;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
import static org.tenpo.challenge.TestHelper.generateUserDAO;

@SpringBootTest
class MyUserDetailsServiceTest extends PostgreSQLMock {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        this.userRepository.deleteAll();
    }

    @Test
    void testLoadUserByUsername_validUser() {
        UserDAO userDAO = generateUserDAO("john");
        this.userRepository.save(userDAO);

        UserDetails userDetails = this.myUserDetailsService.loadUserByUsername("john");

        assertNotNull(userDetails);
    }

    @Test
    void testLoadUserByUsername_invalidUser() {
        assertThrows(UsernameNotFoundException.class, () -> this.myUserDetailsService.loadUserByUsername("invalid_user"));
    }

}