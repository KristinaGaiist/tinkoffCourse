package tinkoff.unit11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.repository.UserRepository;
import unit8.exception.ValidationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    public UserService userService;

    @MockBean
    public UserRepository userRepository;

    private final User user = createRandomUser();

    @Test
    public void saveUserTest() {

        String oldPassword = user.getPassword();
        userService.saveUser(user);

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(argument.capture());

        assertThat(user.getUsername()).isEqualTo(argument.getValue().getUsername());
        assertThat(user.getLastName()).isEqualTo(argument.getValue().getLastName());
        assertThat(user.getFirstName()).isEqualTo(argument.getValue().getFirstName());
        assertThat(oldPassword).isNotEqualTo(argument.getValue().getPassword());
        assertThat(Role.USER).isEqualTo(argument.getValue().getRole());
    }

    @Test
    public void saveAlreadyExistUserTest() {

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        try {
            userService.saveUser(user);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void getRoleByUserNameTest() {

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        Role role = userService.getRoleByUsername(user.getUsername());

        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    public void loadUserByUsernameTest() {

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        assertThat(user.getUsername()).isEqualTo(userDetails.getUsername());
        assertThat(user.getPassword()).isEqualTo(userDetails.getPassword());
    }

    @Test
    public void loadUserByUsernameUserNotFoundTest() {

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        try {
            userService.loadUserByUsername(user.getUsername());
            Assertions.fail("Should have exception thrown");
        } catch (UsernameNotFoundException ignored) {
        }
    }

    private User createRandomUser() {
        User user = new User();
        user.setUsername(RandomStringUtils.random(4));
        user.setLastName(RandomStringUtils.random(40));
        user.setFirstName(RandomStringUtils.random(2));
        user.setPassword(RandomStringUtils.random(10));
        user.setRole(Role.USER);

        return user;
    }
}
