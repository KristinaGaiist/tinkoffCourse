package unit11.service;

import java.util.List;
import org.springframework.stereotype.Service;
import unit11.entity.City;
import unit11.repository.CityRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class CityService {

    private final CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<City> getCities() {
        return repository.findAll();
    }

    public void createCity(String name) {
        if (repository.findByName(name) != null) {
            throw new ValidationException(Message.CITY_ALREADY_EXIST);
        }

        City newCity = new City();
        newCity.setName(name);
        repository.save(newCity);
    }

    public void updateCity(Long id, String name) {
        City city = repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));

        city.setName(name);
        repository.save(city);
    }

    public void deleteCity(long id) {
        repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));

        repository.deleteById(id);
    }
}
