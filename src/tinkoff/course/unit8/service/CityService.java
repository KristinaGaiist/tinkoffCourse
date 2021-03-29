package unit8.service;

import java.util.List;
import java.util.Objects;
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
        return getCityByNameWithValidation(name);
    }

    public City getCityById(long id) {
        return cityRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
    }

    public void createCity(String name) {
        cityRepository.findByName(name)
            .ifPresent(City -> {
                throw new ValidationException(Message.CITY_ALREADY_EXIST);
            });
        cityRepository.addCity(name);
    }

    public void renameCity(String oldName, String newName) {
        City oldCity = getCityByNameWithValidation(oldName);
        cityRepository.findByName(newName)
            .ifPresent(City -> {
                if (!Objects.equals(City.getId(), oldCity.getId())) {
                    throw new ValidationException(Message.CITY_ALREADY_EXIST);
                }
            });
        cityRepository.updateCity(oldCity.getId(), newName);
    }

    public void deleteCity(String name) {
        getCityByNameWithValidation(name);
        cityRepository.deleteCityByName(name);
    }

    private City getCityByNameWithValidation(String name) {
        return cityRepository.findByName(name)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
    }
}
