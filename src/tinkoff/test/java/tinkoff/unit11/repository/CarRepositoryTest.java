package tinkoff.unit11.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Car;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CarRepositoryTest {

    @Autowired
    public CarRepository carRepository;
    @Autowired
    public CustomerRepository customerRepository;
    @Autowired
    public CityRepository cityRepository;
    @Autowired
    public ModelRepository modelRepository;
    @Autowired
    public BrandRepository brandRepository;
    @Autowired
    public UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        user = createUser();
    }

    @Test
    public void findByStateNumberTest() {

        Customer customer1 = createCustomer();
        Customer customer2 = createCustomer();
        Customer customer3 = createCustomer();
        Customer customer4 = createCustomer();

        Car car = createCar();
        car.setCustomers(Set.of(customer1, customer2, customer3, customer4));

        customer1.setCars(Set.of(car));
        customer2.setCars(Set.of(car));
        customer3.setCars(Set.of(car));
        customer4.setCars(Set.of(car));

        carRepository.save(car);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);

        Car result = carRepository.findByStateNumber(car.getStateNumber());
        assertThat(result.getId()).isEqualTo(car.getId());
        assertThat(result.getCustomers()).hasSize(4);
    }

    @Test
    public void carNotFoundTest() {

        Car result = carRepository.findByStateNumber(RandomStringUtils.random(7));
        assertThat(result).isNull();
    }

    @Test
    public void findByCustomersIdTest() {

        Customer customer = createCustomer();
        Car car1 = createCar();
        Car car2 = createCar();
        Car car3 = createCar();
        Car car4 = createCar();

        customer.setCars(Set.of(car1, car2, car3, car4));

        car1.setCustomers(Set.of(customer));
        car2.setCustomers(Set.of(customer));
        car3.setCustomers(Set.of(customer));
        car4.setCustomers(Set.of(customer));

        carRepository.save(car1);
        carRepository.save(car2);
        carRepository.save(car3);
        carRepository.save(car4);
        customerRepository.save(customer);

        Set<Car> resultSet = carRepository.findByCustomers_Id(customer.getId());
        assertThat(resultSet).hasSize(4);
    }

    @Test
    public void customersNotFoundTest() {

        Set<Car> resultSet = carRepository.findByCustomers_Id(Long.parseLong(RandomStringUtils.randomNumeric(7)));
        assertThat(resultSet).isEmpty();
    }

    private Car createCar() {
        Car car = new Car();
        car.setStateNumber(RandomStringUtils.random(6));
        car.setModel(createModel());
        car.setAuthor(user);

        return car;
    }

    private Customer createCustomer() {

        City city = new City();
        city.setName(RandomStringUtils.random(6));
        city.setAuthor(user);
        cityRepository.save(city);

        Customer customer = new Customer();
        customer.setFirstName(RandomStringUtils.random(10));
        customer.setLastName(RandomStringUtils.random(10));
        customer.setMiddleName(RandomStringUtils.random(10));
        customer.setCity(city);
        customer.setAuthor(user);

        return customer;
    }

    private Model createModel() {

        Brand brand = new Brand();
        brand.setName(RandomStringUtils.random(5));
        brand.setAuthor(user);
        brandRepository.save(brand);

        Model model = new Model();
        model.setModel(RandomStringUtils.random(5));
        model.setBrand(brand);
        model.setAuthor(user);
        modelRepository.save(model);

        return model;
    }

    private User createUser() {
        User user = new User();
        user.setUsername(RandomStringUtils.random(5));
        user.setFirstName(RandomStringUtils.random(5));
        user.setLastName(RandomStringUtils.random(5));
        user.setPassword(RandomStringUtils.random(5));
        user.setRole(Role.USER);

        userRepository.save(user);

        return user;
    }
}
