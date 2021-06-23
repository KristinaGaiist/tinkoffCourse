package unit11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unit11.entity.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Model findByModel(String model);
}
