package tinkoff.unit11.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tinkoff.unit11.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Set<Customer> findByCars_Id(long carId);
}
