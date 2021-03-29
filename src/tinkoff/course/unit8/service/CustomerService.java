package unit8.service;

import java.util.List;
import java.util.Objects;
import unit8.data.Message;
import unit8.entity.Car;
import unit8.entity.City;
import unit8.entity.Customer;
import unit8.exception.ValidationException;
import unit8.repository.CarRepository;
import unit8.repository.CustomerRepository;

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public CustomerService(CustomerRepository customerRepository, CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(String firstName, String lastName, String middleName) {
        return customerRepository.findByFullName(firstName, lastName, middleName)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));
    }

    public List<Customer> getCustomersByCarBrand(String brandName) {
        return customerRepository.findCustomersByCarBrand(brandName);
    }

    public Customer getCustomerById(long id) {
        return getCustomerByIdWithValidation(id);
    }

    public void createCustomer(String firstName, String lastName, String middleName, City city) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMiddleName(middleName);
        customer.setCity(city);

        customerRepository.add(customer);
    }

    public void addCustomerCar(long customerId, long carId) {
        getCustomerByIdWithValidation(customerId);
        carRepository.findById(carId)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
        customerRepository.addCustomerCar(customerId, carId);
    }

    public void updateFirstName(long id, String newFirstName) {
        getCustomerByIdWithValidation(id);
        customerRepository.updateFirstName(id, newFirstName);
    }

    public void deleteById(long id) {
        getCustomerByIdWithValidation(id);
        customerRepository.delete(id);
    }

    public void deleteCustomerCar(long customerId, Car car) {
        Customer customer = getCustomerByIdWithValidation(customerId);
        if (customer.getCars().stream().noneMatch(c -> Objects.equals(c.getId(), car.getId()))) {
            throw new ValidationException(Message.CAR_DOES_NOT_BELONG_TO_CUSTOMER);
        }
        customerRepository.deleteCustomerCar(customerId, car);
    }

    private Customer getCustomerByIdWithValidation(long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));
    }
}
