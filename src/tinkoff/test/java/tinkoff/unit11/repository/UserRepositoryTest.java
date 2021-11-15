package tinkoff.unit11.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    public void findByUsernameTest() {
        User user = new User();
        user.setUsername(RandomStringUtils.random(5));
        user.setFirstName(RandomStringUtils.random(5));
        user.setLastName(RandomStringUtils.random(5));
        user.setPassword(RandomStringUtils.random(5));
        user.setRole(Role.USER);

        userRepository.save(user);

        User result = userRepository.findByUsername(user.getUsername());
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(result.getLastName()).isEqualTo(user.getLastName());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getRole()).isEqualTo(user.getRole());
    }
}
