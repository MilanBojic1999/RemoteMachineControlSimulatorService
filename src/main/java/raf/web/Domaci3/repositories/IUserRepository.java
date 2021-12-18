package raf.web.Domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import raf.web.Domaci3.model.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    boolean existsByEmail(String email);
}
