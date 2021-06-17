package unit8.service;

import java.util.List;
import org.springframework.stereotype.Service;
import unit8.data.Message;
import unit8.entity.Car;
import unit8.entity.Model;
import unit8.exception.ValidationException;
import unit8.repository.CarRepository;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(String stateNumber) {
        return carRepository.findByStateNumber(stateNumber)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
    }

    public Car getCarById(long id) {
        return carRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
    }

    public void createCar(String stateNumber, Model model) {
        if (carRepository.existsByStateNumber(stateNumber)) {
            throw new ValidationException(Message.CAR_ALREADY_EXIST);
        }

        Car car = new Car();
        car.setModel(model);
        car.setStateNumber(stateNumber);

        carRepository.add(car);
    }

    public void updateStateNumber(String oldStateNumber, String newStateNumber) {
        Car car = carRepository.findByStateNumber(oldStateNumber)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
        if (carRepository.existsByStateNumber(newStateNumber, car.getId())) {
            throw new ValidationException(Message.CAR_ALREADY_EXIST);
        }

        carRepository.updateStateNumber(car.getId(), newStateNumber);
    }

    public void deleteByStateNumber(String stateNumber) {
        if (!carRepository.existsByStateNumber(stateNumber)) {
            throw new ValidationException(Message.CAR_DOES_NOT_EXIST);
        }
        carRepository.deleteByStateNumber(stateNumber);
    }
}
