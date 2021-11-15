package tinkoff.unit11.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CityRepositoryTest {

    @Autowired
    public CityRepository cityRepository;
    @Autowired
    public UserRepository userRepository;

    @Test
    public void findByNameTest() {
        City city = new City();
        city.setName(RandomStringUtils.random(5));
        city.setAuthor(createUser());

        cityRepository.save(city);

        City result = cityRepository.findByName(city.getName());
        assertThat(result.getId()).isEqualTo(city.getId());
    }

    @Test
    public void cityNotFoundTest() {

        City result = cityRepository.findByName(RandomStringUtils.random(6));
        assertThat(result).isNull();
    }

    private User createUser() {
        User user = new User();
        user.setUsername(RandomStringUtils.random(5));
        user.setFirstName(RandomStringUtils.random(5));
        user.setLastName(RandomStringUtils.random(5));
        user.setPassword(RandomStringUtils.random(5));
        user.setRole(Role.USER);

        userRepository.save(user);

        return user;
    }
}
