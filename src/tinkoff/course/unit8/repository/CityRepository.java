package unit8.repository;

import java.util.List;
import java.util.Optional;
import unit8.entity.City;

public interface CityRepository {

    void addCity(String cityName);

    List<City> findAllCities();

    Optional<City> findByName(String cityName);

    Optional<City> findById(long id);

    void updateCity(long id, String newName);

    void deleteCityByName(String cityName);
}
