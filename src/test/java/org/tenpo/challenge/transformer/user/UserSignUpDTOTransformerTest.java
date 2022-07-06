package org.tenpo.challenge.transformer.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.tenpo.challenge.model.dto.user.UserSignupDTO;
import org.tenpo.challenge.model.internal.User;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserSignUpDTOTransformerTest {

    @Resource
    private UserSignUpDTOTransformer transformer;

    @Test
    void testTransform() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        UserSignupDTO dto = this.transformer.transform(user);

        assertEquals(1L, dto.getId());
        assertEquals("john", dto.getUsername());
    }
}