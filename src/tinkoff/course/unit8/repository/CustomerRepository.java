package unit8.repository;

import java.util.List;
import java.util.Optional;
import unit8.entity.Car;
import unit8.entity.Customer;

public interface CustomerRepository {

    void add(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findByFullName(String firstName, String lastName, String middleName);

    Optional<Customer> findById(long id);

    List<Customer> findCustomersByCarBrand(String brandName);

    List<Customer> findCustomersByCarModel(String model);

    void updateFirstName(long id, String newFirstName);

    void addCustomerCar(long customerId, long carId);

    void delete(long id);

    void deleteCustomerCar(long customerId, Car car);
}