package tinkoff.unit11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.CarRepository;
import tinkoff.unit11.repository.CustomerRepository;
import tinkoff.unit11.repository.ModelRepository;
import unit8.exception.ValidationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CarServiceTest {

    @Autowired
    public CarService carService;

    @MockBean
    public CarRepository carRepository;
    @MockBean
    public ModelRepository modelRepository;
    @MockBean
    public CustomerRepository customerRepository;
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
    public void createCarTest() {

        Model model = createModel();

        when(modelRepository.findById(model.getId())).thenReturn(Optional.of(model));

        String stateNumber = RandomStringUtils.random(6);
        carService.createCar(stateNumber, model);

        ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
        verify(carRepository, times(1)).save(argument.capture());
        assertThat(stateNumber).isEqualTo(argument.getValue().getStateNumber());
        assertThat(model).isEqualTo(argument.getValue().getModel());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
        assertThat(argument.getValue().getCustomers()).isEmpty();
    }

    @Test
    public void createAlreadyExistCarTest() {

        Model model = createModel();
        when(modelRepository.findByModel(model.getModel())).thenReturn(model);

        Car car = new Car();
        car.setStateNumber(RandomStringUtils.random(6));

        try {
            carService.createCar(car.getStateNumber(), model);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void createModelDoesNotExistTest() {

        when(modelRepository.findById(any())).thenReturn(Optional.empty());

        try {
            carService.createCar(RandomStringUtils.random(6), createModel());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void getAllCarsTest() {

        Model model = createModel();
        Car car1 = createCar(model);
        Car car2 = createCar(model);
        List<Car> cars = List.of(car1, car2);

        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getCars();
        assertThat(result).hasSize(cars.size());
        assertThat(result).isEqualTo(cars);
    }

    @Test
    public void getCarsByCustomerIdTest() {

        Model model = createModel();
        Customer customer = createCustomer();

        Car car1 = createCar(model);
        car1.setCustomers(Set.of(customer));

        Car car2 = createCar(model);
        car2.setCustomers(Set.of(customer));

        customer.setCars(Set.of(car1, car2));
        Set<Car> cars = Set.of(car1, car2);

        when(carRepository.findByCustomers_Id(customer.getId())).thenReturn(cars);

        Set<Car> result = carService.getCarsByCustomerId(customer.getId());
        assertThat(result).hasSize(cars.size());
        assertThat(result).isEqualTo(cars);
    }

    @Test
    public void updateCarStateNumberTest() {

        Model model = createModel();
        Car car = createCar(model);

        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        String newStateNumber = RandomStringUtils.random(4);
        carService.updateCarStateNumber(car.getId(), newStateNumber);

        ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
        verify(carRepository, times(1)).save(argument.capture());
        assertThat(newStateNumber).isEqualTo(argument.getValue().getStateNumber());
        assertThat(model).isEqualTo(argument.getValue().getModel());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void updateDoesNotExistCarTest() {

        Long id = 5L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        try {
            carService.updateCarStateNumber(id, RandomStringUtils.random(6));
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void deleteCarTest() {

        Car car = createCar(createModel());
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        carService.deleteCar(car.getId());

        verify(carRepository, times(1)).deleteById(car.getId());
    }

    @Test
    public void deleteDoesNotExistCarTest() {

        long id = 5L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        try {
            carService.deleteCar(id);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void addCustomerTest() {

        Car car = createCar(createModel());
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        Customer customer = createCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        carService.addCustomer(car.getId(), customer.getId());

        ArgumentCaptor<Car> argument = ArgumentCaptor.forClass(Car.class);
        verify(carRepository, times(1)).save(argument.capture());
        assertThat(car.getStateNumber()).isEqualTo(argument.getValue().getStateNumber());
        assertThat(car.getModel()).isEqualTo(argument.getValue().getModel());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
        assertThat(Set.of(customer)).isEqualTo(argument.getValue().getCustomers());
    }

    @Test
    public void addCustomerCarDoesNotExistTest() {

        Car car = createCar(createModel());
        when(carRepository.findById(car.getId())).thenReturn(Optional.empty());

        try {
            carService.addCustomer(car.getId(), 6L);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void addCustomerAlreadyAddedTest() {

        Customer customer = createCustomer();
        Car car = createCar(createModel());
        car.setCustomers(Set.of(customer));
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        try {
            carService.addCustomer(car.getId(), customer.getId());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void addCustomerDoesNotExistTest() {

        Car car = createCar(createModel());
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));

        long customerId = 6L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        try {
            carService.addCustomer(car.getId(), customerId);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(carRepository, never()).save(any(Car.class));
    }

    private Model createModel() {
        Brand brand = new Brand();
        brand.setId(5L);
        brand.setName(RandomStringUtils.random(5));

        Model model = new Model();
        model.setId(6L);
        model.setModel(RandomStringUtils.random(5));
        model.setBrand(brand);
        model.setAuthor(user);

        return model;
    }

    private Car createCar(Model model) {
        Car car = new Car();
        car.setId(Long.parseLong(RandomStringUtils.randomNumeric(3)));
        car.setStateNumber(RandomStringUtils.random(4));
        car.setAuthor(user);
        car.setModel(model);

        return car;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAuthor(user);
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));

        return customer;
    }
}
