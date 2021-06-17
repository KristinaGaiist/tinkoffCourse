package unit11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unit11.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
