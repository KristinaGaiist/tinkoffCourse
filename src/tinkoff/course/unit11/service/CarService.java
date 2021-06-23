package unit11.service;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import unit11.entity.Car;
import unit11.entity.Customer;
import unit11.entity.Model;
import unit11.repository.CarRepository;
import unit11.repository.CustomerRepository;
import unit11.repository.ModelRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final CustomerRepository customerRepository;

    public CarService(CarRepository carRepository, ModelRepository modelRepository, CustomerRepository customerRepository) {
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
        this.customerRepository = customerRepository;
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

        Car car = new Car();
        car.setStateNumber(stateNumber);
        car.setModel(modelEntity);

        carRepository.save(car);
    }

    public void updateCarStateNumber(Long id, String stateNumber) {
        Car car = carRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));

        car.setStateNumber(stateNumber);

        carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));

        carRepository.deleteById(id);
    }

    public void deleteCustomer(long carId, long customerId) {
        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));

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
