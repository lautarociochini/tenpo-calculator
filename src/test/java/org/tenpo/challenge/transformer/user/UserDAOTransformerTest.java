package org.tenpo.challenge.transformer.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.model.dao.UserDAO;
import org.tenpo.challenge.model.internal.User;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.tenpo.challenge.TestHelper.generateUserDAO;

@SpringBootTest
class UserDAOTransformerTest {

    @Resource
    private UserDAOTransformer userDAOTransformer;

    @Test
    void testTransform() {
        UserDAO userDAO = generateUserDAO("john");
        userDAO.setCreatedAt(LocalDateTime.now());
        userDAO.setId(1L);

        User user = this.userDAOTransformer.transform(userDAO);

        assertEquals(userDAO.getCreatedAt(), user.getCreatedAt());
        assertEquals(userDAO.getPassword(), user.getPassword());
        assertEquals(userDAO.getUsername(), user.getUsername());
        assertEquals(userDAO.getEmail(), user.getEmail());
        assertEquals(userDAO.getId(), user.getId());
    }
}