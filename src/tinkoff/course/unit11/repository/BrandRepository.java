package unit11.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unit11.entity.Brand;

@Repository
@Transactional
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findAll();
    Brand findByName(String name);
}
