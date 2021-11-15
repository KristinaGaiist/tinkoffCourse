package tinkoff.unit11.service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import tinkoff.unit11.entity.Car;
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.CarRepository;
import tinkoff.unit11.repository.CustomerRepository;
import tinkoff.unit11.repository.ModelRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final CustomerRepository customerRepository;
    private final IUserAccessor userAccessor;
    private final PermissionGuard permissionGuard;

    public CarService(CarRepository carRepository, ModelRepository modelRepository,
                      CustomerRepository customerRepository, IUserAccessor userAccessor,
                      PermissionGuard permissionGuard) {
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
        this.customerRepository = customerRepository;
        this.userAccessor = userAccessor;
        this.permissionGuard = permissionGuard;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Set<Car> getCarsByCustomerId(long customerId) {
        return carRepository.findByCustomers_Id(customerId);
    }

    public void createCar(String stateNumber, Model model) {

        if (carRepository.findByStateNumber(stateNumber) != null) {
            throw new ValidationException(Message.CAR_ALREADY_EXIST);
        }

        Model modelEntity = modelRepository.findById(model.getId())
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));

        User user = userAccessor.getCurrentUser();
        Car car = new Car();
        car.setStateNumber(stateNumber);
        car.setModel(modelEntity);
        car.setAuthor(user);

        carRepository.save(car);
    }

    public void updateCarStateNumber(Long id, String stateNumber) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(car.getAuthor());

        car.setStateNumber(stateNumber);

        carRepository.save(car);
    }

    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(car.getAuthor());

        carRepository.deleteById(id);
    }

    public void deleteCustomer(long carId, long customerId) {
        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(car.getAuthor());

        Customer customer = car.getCustomers().stream()
            .filter(c -> c.getId() == customerId)
            .findFirst()
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_BELONG_TO_CUSTOMER));

        car.getCustomers().remove(customer);
        customer.getCars().remove(car);

        carRepository.save(car);
    }

    public void addCustomer(long carId, long customerId) {
        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(car.getAuthor());

        boolean alreadyHasCustomer = car.getCustomers().stream()
            .anyMatch(c -> c.getId() == customerId);

        if (alreadyHasCustomer) {
            throw new ValidationException(Message.CAR_ALREADY_BELONG_TO_CUSTOMER);
        }

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new ValidationException(Message.CUSTOMER_DOES_NOT_EXIST));

        car.getCustomers().add(customer);
        customer.getCars().add(car);
        carRepository.save(car);
    }
}
