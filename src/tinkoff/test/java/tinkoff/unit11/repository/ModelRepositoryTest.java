package tinkoff.unit11.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ModelRepositoryTest {

    @Autowired
    public ModelRepository modelRepository;
    @Autowired
    public BrandRepository brandRepository;
    @Autowired
    public UserRepository userRepository;

    @Test
    public void findByModelTest() {
        Model model = new Model();
        model.setModel(RandomStringUtils.random(5));
        model.setBrand(createBrand());
        model.setAuthor(createUser());

        modelRepository.save(model);

        Model result = modelRepository.findByModel(model.getModel());
        assertThat(result.getId()).isEqualTo(model.getId());
    }

    @Test
    public void modelNotFoundTest() {

        Model result = modelRepository.findByModel(RandomStringUtils.random(6));
        assertThat(result).isNull();
    }

    private Brand createBrand() {
        Brand brand = new Brand();
        brand.setName(RandomStringUtils.random(5));
        brand.setAuthor(createUser());

        brandRepository.save(brand);

        return brand;
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
