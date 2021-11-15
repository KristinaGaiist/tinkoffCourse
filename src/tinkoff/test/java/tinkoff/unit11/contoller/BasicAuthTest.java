package tinkoff.unit11.contoller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tinkoff.unit11.controller.BasicAuthController;
import tinkoff.unit11.entity.Role;
import tinkoff.unit11.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class BasicAuthTest {

    private static final String URL = "/basicAuth";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public BasicAuthController basicAuthController;

    @MockBean
    public UserService userService;

    @Test
    public void authTest() throws Exception {

        when(userService.getRoleByUsername(any(String.class))).thenReturn(Role.USER);

        mockMvc
            .perform(get(URL))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("You are authenticated"))
            .andExpect(jsonPath("$.role").value("USER"));
    }
}
