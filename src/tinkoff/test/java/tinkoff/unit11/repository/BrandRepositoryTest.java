package tinkoff.unit11.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BrandRepositoryTest {

    @Autowired
    public BrandRepository brandRepository;
    @Autowired
    public UserRepository userRepository;

    @Test
    public void findByNameTest() {
        Brand brand = new Brand();
        brand.setName(RandomStringUtils.random(5));
        brand.setAuthor(createUser());

        brandRepository.save(brand);

        Brand result = brandRepository.findByName(brand.getName());
        assertThat(result.getId()).isEqualTo(brand.getId());
    }

    @Test
    public void brandNotFoundTest() {

        Brand result = brandRepository.findByName(RandomStringUtils.random(6));
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
