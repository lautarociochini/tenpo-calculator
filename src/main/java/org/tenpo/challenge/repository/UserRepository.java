package org.tenpo.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tenpo.challenge.model.dao.UserDAO;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

    UserDAO findByUsername(String username);

}
