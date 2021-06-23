package unit11.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unit11.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName);
    Set<Customer> findByCars_Id(long carId);
}
