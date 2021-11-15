package tinkoff.unit11.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tinkoff.unit11.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Car findByStateNumber(String stateNumber);

    Set<Car> findByCustomers_Id(long customerId);
}
