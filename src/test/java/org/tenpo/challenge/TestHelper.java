package org.tenpo.challenge;

import org.tenpo.challenge.model.dao.OperationDAO;
import org.tenpo.challenge.model.dao.UserDAO;

public class TestHelper {

    public static OperationDAO createOperationDAO(String operationType) {
        OperationDAO operationDAO = new OperationDAO();
        operationDAO.setType(operationType);

        return operationDAO;
    }

    public static UserDAO generateUserDAO(String username) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUsername(username);
        userDAO.setPassword("pass");
        userDAO.setEmail("example_email@test.com");

        return userDAO;
    }
}
