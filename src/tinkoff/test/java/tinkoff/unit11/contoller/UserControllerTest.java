package tinkoff.unit11.contoller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.TestUtil;
import tinkoff.unit11.controller.UserController;
import tinkoff.unit11.controller.contracts.RegisterUserRequest;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class UserControllerTest {

    private static final String URL = "/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public UserController userController;

    @MockBean
    public UserService userService;
    @MockBean
    public IUserAccessor userAccessor;

    @Test
    public void registerTest() throws Exception {

        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName(RandomStringUtils.random(5));
        request.setLastName(RandomStringUtils.random(5));
        request.setUsername(RandomStringUtils.random(5));
        request.setPassword(RandomStringUtils.random(5));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

        doNothing().when(userService).saveUser(user);

        mockMvc.perform(post(URL + "/registration")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void registerWithNullFieldsTest() throws Exception {

        mockMvc.perform(post(URL + "/registration")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(new RegisterUserRequest()))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void getCurrentUserTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(4));
        user.setFirstName(RandomStringUtils.random(4));
        user.setLastName(RandomStringUtils.random(4));
        user.setPassword(RandomStringUtils.random(4));
        user.setRole(Role.USER);

        when(userAccessor.getCurrentUser()).thenReturn(user);

        mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()))
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.role").value(user.getRole().toString()));
    }

}
