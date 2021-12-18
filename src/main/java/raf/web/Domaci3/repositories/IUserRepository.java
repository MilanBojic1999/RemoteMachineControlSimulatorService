package raf.web.Domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.web.Domaci3.model.User;

public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByUserId(Long userId);
}
