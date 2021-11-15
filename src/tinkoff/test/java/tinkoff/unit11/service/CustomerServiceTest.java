package tinkoff.unit11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Car;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.CarRepository;
import tinkoff.unit11.repository.CityRepository;
import tinkoff.unit11.repository.CustomerRepository;
import unit8.exception.ValidationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @Autowired
    public CustomerService customerService;

    @MockBean
    public CustomerRepository customerRepository;
    @MockBean
    public CarRepository carRepository;
    @MockBean
    public CityRepository cityRepository;
    @MockBean
    public IUserAccessor userAccessor;
    @MockBean
    public PermissionGuard permissionGuard;

    private final User user = new User();

    @BeforeEach
    public void init() {
        when(userAccessor.getCurrentUser()).thenReturn(user);
        doNothing().when(permissionGuard).ensureCanModify(any(User.class));
    }

    @Test
    public void createCustomerTest() {

        City city = createCity();

        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        String firstName = RandomStringUtils.random(6);
        String lastName = RandomStringUtils.random(6);
        String middleName = RandomStringUtils.random(6);

        customerService.createCustomer(firstName, lastName, middleName, city);

        ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(argument.capture());
        assertThat(firstName).isEqualTo(argument.getValue().getFirstName());
        assertThat(lastName).isEqualTo(argument.getValue().getLastName());
        assertThat(middleName).isEqualTo(argument.getValue().getMiddleName());
        assertThat(city).isEqualTo(argument.getValue().getCity());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
        assertThat(argument.getValue().getCars()).isEmpty();
    }

    @Test
    public void createCityDoesNotExistTest() {

        when(cityRepository.findById(any())).thenReturn(Optional.empty());

        try {
            customerService.createCustomer(RandomStringUtils.random(6),
                                           RandomStringUtils.random(6),
                                           RandomStringUtils.random(6),
                                           createCity());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void getAllCustomersTest() {

        City city = createCity();
        Customer customer1 = createCustomer(city);
        Customer customer2 = createCustomer(city);

        List<Customer> customers = List.of(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getCustomers();
        assertThat(result).hasSize(customers.size());
        assertThat(result).isEqualTo(customers);
    }

    @Test
    public void getCustomersByCarIdTest() {

        City city = createCity();
        Car car = createCar();

        Customer customer1 = createCustomer(city);
        customer1.setCars(Set.of(car));
        Customer customer2 = createCustomer(city);
        customer2.setCars(Set.of(car));
        Customer customer3 = createCustomer(city);
        customer3.setCars(Set.of(car));

        car.setCustomers(Set.of(customer1, customer2, customer3));

        Set<Customer> customers = Set.of(customer1, customer2, customer3);

        when(customerRepository.findByCars_Id(car.getId())).thenReturn(customers);

        Set<Customer> result = customerService.getCustomersByCarId(car.getId());
        assertThat(result).hasSize(customers.size());
        assertThat(result).isEqualTo(customers);
    }

    @Test
    public void updateCustomerInfoTest() {

        City newCity = createCity();
        Customer customer = createCustomer(createCity());
        customer.setCars(Set.of(createCar(), createCar()));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cityRepository.findById(newCity.getId())).thenReturn(Optional.of(newCity));

        String newFirstName = RandomStringUtils.random(6);
        String newLastName = RandomStringUtils.random(6);
        String newMiddleName = RandomStringUtils.random(6);
        customerService.updateCustomerInfo(customer.getId(), newFirstName, newLastName, newMiddleName, newCity);

        ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(argument.capture());
        assertThat(newFirstName).isEqualTo(argument.getValue().getFirstName());
        assertThat(newLastName).isEqualTo(argument.getValue().getLastName());
        assertThat(newMiddleName).isEqualTo(argument.getValue().getMiddleName());
        assertThat(newCity).isEqualTo(argument.getValue().getCity());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
        assertThat(customer.getCars()).isEqualTo(argument.getValue().getCars());
    }

    @Test
    public void updateCustomerInfoDoesNotExistCustomerTest() {

        long id = 5L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        try {
            customerService.updateCustomerInfo(id,
                                               RandomStringUtils.random(6),
                                               RandomStringUtils.random(6),
                                               RandomStringUtils.random(6),
                                               createCity());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void updateCustomerInfoDoesNotExistCityTest() {

        Customer customer = createCustomer(createCity());
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(cityRepository.findById(any())).thenReturn(Optional.empty());

        try {
            customerService.updateCustomerInfo(customer.getId(),
                                               RandomStringUtils.random(6),
                                               RandomStringUtils.random(6),
                                               RandomStringUtils.random(6),
                                               createCity());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void deleteCustomerTest() {

        Customer customer = createCustomer(createCity());
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(customer.getId());

        verify(customerRepository, times(1)).deleteById(customer.getId());
    }

    @Test
    public void deleteDoesNotExistCustomerTest() {

        long id = 5L;
        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        try {
            customerService.deleteCustomer(id);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void addCarTest() {

        Customer customer = createCustomer(createCity());
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Car car = createCar();
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        customerService.addCar(customer.getId(), car.getId());

        ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(argument.capture());
        assertThat(customer.getFirstName()).isEqualTo(argument.getValue().getFirstName());
        assertThat(customer.getLastName()).isEqualTo(argument.getValue().getLastName());
        assertThat(customer.getMiddleName()).isEqualTo(argument.getValue().getMiddleName());
        assertThat(customer.getCity()).isEqualTo(argument.getValue().getCity());
        assertThat(customer.getAuthor()).isEqualTo(argument.getValue().getAuthor());
        assertThat(Set.of(car)).isEqualTo(argument.getValue().getCars());
    }

    @Test
    public void addCarCustomerDoesNotExistTest() {

        Customer customer = createCustomer(createCity());
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        try {
            customerService.addCar(customer.getId(), 6L);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void addCarAlreadyAddedTest() {

        Customer customer = createCustomer(createCity());
        Car car = createCar();
        customer.setCars(Set.of(car));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        try {
            customerService.addCar(customer.getId(), car.getId());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void addCarDoesNotExistTest() {

        Customer customer = createCustomer(createCity());
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        long carId = 6L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        try {
            customerService.addCar(customer.getId(), carId);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void deleteCarTest() {

        Car car = createCar();
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        Customer customer = createCustomer(createCity());
        customer.setCars(cars);
        Set<Customer> customers = new HashSet<>();
        customers.add(customer);
        car.setCustomers(customers);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        customerService.deleteCar(customer.getId(), car.getId());

        ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository, times(1)).save(argument.capture());
        assertThat(customer.getFirstName()).isEqualTo(argument.getValue().getFirstName());
        assertThat(customer.getLastName()).isEqualTo(argument.getValue().getLastName());
        assertThat(customer.getMiddleName()).isEqualTo(argument.getValue().getMiddleName());
        assertThat(customer.getCity()).isEqualTo(argument.getValue().getCity());
        assertThat(customer.getAuthor()).isEqualTo(argument.getValue().getAuthor());
        assertThat(argument.getValue().getCars()).isEmpty();
    }

    @Test
    public void deleteCarCustomerDoesNotExistTest() {

        Car car = createCar();
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        Customer customer = createCustomer(createCity());
        customer.setCars(cars);
        Set<Customer> customers = new HashSet<>();
        customers.add(customer);
        car.setCustomers(customers);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        try {
            customerService.deleteCar(customer.getId(), createCar().getId());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    public void deleteCarDoesNotBelongToCustomerTest() {

        long id = 5L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        try {
            customerService.deleteCar(id, id);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(customerRepository, never()).save(any(Customer.class));
    }

    private City createCity() {
        City city = new City();
        city.setId(5L);
        city.setName(RandomStringUtils.random(5));

        return city;
    }

    private Car createCar() {
        Brand brand = new Brand();
        brand.setId(5L);
        brand.setName(RandomStringUtils.random(5));

        Model model = new Model();
        model.setId(6L);
        model.setModel(RandomStringUtils.random(5));
        model.setBrand(brand);
        model.setAuthor(user);

        Car car = new Car();
        car.setId(Long.parseLong(RandomStringUtils.randomNumeric(3)));
        car.setStateNumber(RandomStringUtils.random(4));
        car.setAuthor(user);
        car.setModel(model);

        return car;
    }

    private Customer createCustomer(City city) {
        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setAuthor(user);
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));
        customer.setCity(city);

        return customer;
    }
}
