package tinkoff.unit11.service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import tinkoff.unit11.entity.Car;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.CarRepository;
import tinkoff.unit11.repository.CityRepository;
import tinkoff.unit11.repository.CustomerRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CityRepository cityRepository;
    private final CarRepository carRepository;
    private final IUserAccessor userAccessor;
    private final PermissionGuard permissionGuard;

    public CustomerService(CustomerRepository customerRepository,
                           CityRepository cityRepository,
                           CarRepository carRepository, IUserAccessor userAccessor,
                           PermissionGuard permissionGuard) {
        this.customerRepository = customerRepository;
        this.cityRepository = cityRepository;
        this.carRepository = carRepository;
        this.userAccessor = userAccessor;
        this.permissionGuard = permissionGuard;
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

        User user = userAccessor.getCurrentUser();
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMiddleName(middleName);
        customer.setCity(cityEntity);
        customer.setAuthor(user);

        customerRepository.save(customer);
    }

    public void updateCustomerInfo(Long id, String firstName, String lastName, String middleName,
                                   City city) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(customer.getAuthor());
        City cityEntity = cityRepository.findById(city.getId())
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMiddleName(middleName);
        customer.setCity(cityEntity);

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(customer.getAuthor());

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
        permissionGuard.ensureCanModify(car.getAuthor());

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
        permissionGuard.ensureCanModify(car.getAuthor());

        customer.getCars().remove(car);
        car.getCustomers().remove(customer);

        customerRepository.save(customer);
    }
}
