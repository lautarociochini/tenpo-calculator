package org.tenpo.challenge.transformer.user;

import org.springframework.stereotype.Component;
import org.tenpo.challenge.model.dao.UserDAO;
import org.tenpo.challenge.model.internal.User;
import org.tenpo.challenge.transformer.Transformer;

@Component
public class UserDAOTransformer implements Transformer<UserDAO, User> {

    @Override
    public User transform(UserDAO request) {
        User user = new User();
        user.setCreatedAt(request.getCreatedAt());
        user.setEmail(request.getEmail());
        user.setId(request.getId());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());

        return user;
    }
}
