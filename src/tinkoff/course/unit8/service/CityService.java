package unit8.service;

import java.util.List;
import unit8.data.Message;
import unit8.entity.City;
import unit8.exception.ValidationException;
import unit8.repository.CityRepository;

public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAllCities();
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
    }

    public City getCityById(long id) {
        return cityRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
    }

    public void createCity(String name) {
        if (cityRepository.existByName(name)) {
            throw new ValidationException(Message.CITY_ALREADY_EXIST);
        }
        cityRepository.addCity(name);
    }

    public void renameCity(String oldName, String newName) {
        City city = cityRepository.findByName(oldName)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
        if (cityRepository.existsByName(newName, city.getId())) {
            throw new ValidationException(Message.CITY_ALREADY_EXIST);
        }

        cityRepository.updateCity(city.getId(), newName);
    }

    public void deleteCity(String name) {
        if (!cityRepository.existByName(name)) {
            throw new ValidationException(Message.CITY_DOES_NOT_EXIST);
        }
        cityRepository.deleteCityByName(name);
    }
}
