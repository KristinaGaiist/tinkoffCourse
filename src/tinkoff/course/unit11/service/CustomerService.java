package unit11.service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import unit11.entity.Car;
import unit11.entity.City;
import unit11.entity.Customer;
import unit11.repository.CarRepository;
import unit11.repository.CityRepository;
import unit11.repository.CustomerRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CityRepository cityRepository;
    private final CarRepository carRepository;

    public CustomerService(CustomerRepository customerRepository,
                           CityRepository cityRepository,
                           CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.cityRepository = cityRepository;
        this.carRepository = carRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Set<Customer> getCustomersByCarId(long carId) {
        return customerRepository.findByCars_Id(carId);
    }

    public void createCustomer(String firstName, String lastName, String middleName,
                               City city) {

        City cityEntity = cityRepository.findById(city.getId())
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMiddleName(middleName);
        customer.setCity(cityEntity);

        customerRepository.save(customer);
    }

    public void updateCustomerInfo(Long id, String firstName, String lastName, String middleName,
                                   City city) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));
        City cityEntity = cityRepository.findById(city.getId())
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMiddleName(middleName);
        customer.setCity(cityEntity);

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));

        customerRepository.deleteById(id);
    }

    public void addCar(long customerId, long carId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));

        boolean alreadyHasCar = customer.getCars().stream()
            .anyMatch(c -> c.getId() == carId);

        if (alreadyHasCar) {
            throw new ValidationException(Message.CAR_ALREADY_BELONG_TO_CUSTOMER);
        }

        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));

        customer.getCars().add(car);
        car.getCustomers().add(customer);
        customerRepository.save(customer);
    }

    public void deleteCar(long customerId, long carId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));

        Car car = customer.getCars().stream()
            .filter(c -> c.getId() == carId)
            .findFirst()
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_BELONG_TO_CUSTOMER));

        customer.getCars().remove(car);
        car.getCustomers().remove(customer);

        customerRepository.save(customer);
    }
}
