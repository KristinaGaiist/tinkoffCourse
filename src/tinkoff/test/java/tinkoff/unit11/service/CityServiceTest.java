package tinkoff.unit11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.CityRepository;
import unit8.exception.ValidationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CityServiceTest {

    @Autowired
    public CityService cityService;

    @MockBean
    public CityRepository cityRepository;
    @MockBean
    public IUserAccessor userAccessor;
    @MockBean
    public PermissionGuard permissionGuard;

    private final User user = new User();

    @BeforeEach
    public void init() {
        when(userAccessor.getCurrentUser()).thenReturn(user);
        doNothing().when(permissionGuard).ensureCanModify(any(User.class));
    }

    @Test
    public void createCityTest() {

        String name = RandomStringUtils.random(4);
        cityService.createCity(name);

        ArgumentCaptor<City> argument = ArgumentCaptor.forClass(City.class);
        verify(cityRepository, times(1)).save(argument.capture());
        assertThat(name).isEqualTo(argument.getValue().getName());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void createAlreadyExistCityTest() {

        City city = createCity();
        when(cityRepository.findByName(city.getName())).thenReturn(city);

        try {
            cityService.createCity(city.getName());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    public void getAllCitiesTest() {

        City city1 = createCity();
        City city2 = createCity();
        City city3 = createCity();
        List<City> cities = List.of(city1, city2, city3);

        when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.getCities();
        assertThat(result).hasSize(cities.size());
        assertThat(result).isEqualTo(cities);
    }

    @Test
    public void updateCityTest() {

        City city = createCity();
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        String newName = RandomStringUtils.random(4);
        cityService.updateCity(city.getId(), newName);

        ArgumentCaptor<City> argument = ArgumentCaptor.forClass(City.class);
        verify(cityRepository, times(1)).save(argument.capture());
        assertThat(newName).isEqualTo(argument.getValue().getName());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void updateDoesNotExistCityTest() {

        Long id = 5L;
        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        try {
            cityService.updateCity(id, RandomStringUtils.random(4));
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    public void deleteCityTest() {

        City city = createCity();
        when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        cityService.deleteCity(city.getId());

        verify(cityRepository, times(1)).deleteById(city.getId());
    }

    @Test
    public void deleteDoesNotExistCityTest() {

        long id = 5L;
        when(cityRepository.findById(id)).thenReturn(Optional.empty());

        try {
            cityService.deleteCity(id);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(cityRepository, never()).save(any(City.class));
    }

    private City createCity() {
        City city = new City();
        city.setId(Long.parseLong(RandomStringUtils.randomNumeric(5)));
        city.setName(RandomStringUtils.random(5));
        city.setAuthor(user);

        return city;
    }
}
