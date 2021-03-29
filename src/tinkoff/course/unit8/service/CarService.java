package unit8.service;

import java.util.List;
import java.util.Objects;
import unit8.data.Message;
import unit8.entity.Car;
import unit8.entity.Model;
import unit8.exception.ValidationException;
import unit8.repository.CarRepository;

public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCar(String stateNumber) {
        return getCarByStateNumberWithValidation(stateNumber);
    }

    public Car getCarById(long id) {
        return carRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
    }

    public void createCar(String stateNumber, Model model) {
        carRepository.findByStateNumber(stateNumber)
            .ifPresent(Model -> {
                throw new ValidationException(Message.CAR_ALREADY_EXIST);
            });

        Car car = new Car();
        car.setModel(model);
        car.setStateNumber(stateNumber);

        carRepository.add(car);
    }

    public void updateStateNumber(String oldModel, String newStateNumber) {
        Car car = getCarByStateNumberWithValidation(oldModel);
        carRepository.findByStateNumber(newStateNumber)
            .ifPresent(c -> {
                if (!Objects.equals(c.getId(), car.getId())) {
                    throw new ValidationException(Message.CAR_ALREADY_EXIST);
                }
            });
        carRepository.updateStateNumber(car.getId(), newStateNumber);
    }

    public void deleteByStateNumber(String stateNumber) {
        getCarByStateNumberWithValidation(stateNumber);
        carRepository.deleteByStateNumber(stateNumber);
    }

    private Car getCarByStateNumberWithValidation(String stateNumber) {
        return carRepository.findByStateNumber(stateNumber)
            .orElseThrow(() -> new ValidationException(Message.CAR_DOES_NOT_EXIST));
    }
}
