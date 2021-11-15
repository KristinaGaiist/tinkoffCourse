package tinkoff.unit11.service;

import java.util.List;
import org.springframework.stereotype.Service;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.CityRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class CityService {

    private final CityRepository repository;
    private final IUserAccessor userAccessor;
    private final PermissionGuard permissionGuard;

    public CityService(CityRepository repository, IUserAccessor userAccessor,
                       PermissionGuard permissionGuard) {
        this.repository = repository;
        this.userAccessor = userAccessor;
        this.permissionGuard = permissionGuard;
    }

    public List<City> getCities() {
        return repository.findAll();
    }

    public void createCity(String name) {
        if (repository.findByName(name) != null) {
            throw new ValidationException(Message.CITY_ALREADY_EXIST);
        }

        User user = userAccessor.getCurrentUser();
        City newCity = new City();
        newCity.setName(name);
        newCity.setAuthor(user);
        repository.save(newCity);
    }

    public void updateCity(Long id, String name) {
        City city = repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(city.getAuthor());

        city.setName(name);
        repository.save(city);
    }

    public void deleteCity(long id) {
        City city = repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.CITY_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(city.getAuthor());

        repository.deleteById(id);
    }
}
