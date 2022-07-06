package org.tenpo.challenge.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tenpo.challenge.exception.DuplicatedUserException;
import org.tenpo.challenge.model.dao.UserDAO;
import org.tenpo.challenge.model.dto.user.SignupRequest;
import org.tenpo.challenge.model.internal.User;
import org.tenpo.challenge.repository.UserRepository;
import org.tenpo.challenge.transformer.Transformer;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource(name = "getPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Resource(name = "userDAOTransformer")
    private Transformer<UserDAO, User> transformer;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User signIn(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        if (this.userExists(username)) {
            String message = String.format("User %s already exists", username);
            logger.info(message);
            throw new DuplicatedUserException(message);
        }

        UserDAO user = this.saveUser(signupRequest);
        return this.transformer.transform(user);
    }

    @Override
    public User findUserByUsername(String username) {
        UserDAO user = this.userRepository.findByUsername(username);
        return user != null ? this.transformer.transform(user) : null;
    }

    private UserDAO saveUser(SignupRequest signupRequest) {
        UserDAO userDAO = new UserDAO();
        userDAO.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userDAO.setUsername(signupRequest.getUsername());
        userDAO.setEmail(signupRequest.getEmail());
        return this.userRepository.save(userDAO);
    }

    private boolean userExists(String username) {
        return this.findUserByUsername(username) != null;
    }
}
