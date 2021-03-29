package unit8.repository;

import java.util.List;
import java.util.Optional;
import unit8.entity.Car;

public interface CarRepository {

    void add(Car car);

    List<Car> findAll();

    Optional<Car> findByStateNumber(String model);

    Optional<Car> findById(long id);

    void updateStateNumber(long id, String newStateNumber);

    void deleteByStateNumber(String stateNumber);
}