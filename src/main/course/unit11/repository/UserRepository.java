package unit11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unit11.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
