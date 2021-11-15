package tinkoff.unit11.contoller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
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
import tinkoff.unit11.controller.CityController;
import tinkoff.unit11.controller.contracts.CityDto;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.service.CityService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class CityControllerTest {

    private static final String URL = "/cities";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public CityController cityController;

    @MockBean
    public CityService cityService;

    @Test
    public void createCityTest() throws Exception {

        CityDto request = new CityDto();
        request.setId(1L);
        request.setName(RandomStringUtils.random(6));

        doNothing().when(cityService).createCity(request.getName());

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void createCityWithEmptyNameTest() throws Exception {

        CityDto request = new CityDto();
        request.setId(1L);
        request.setName("");

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getCitiesTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(6));

        City city1 = new City();
        city1.setId(5L);
        city1.setName(RandomStringUtils.random(5));
        city1.setAuthor(user);

        City city2 = new City();
        city2.setId(15L);
        city2.setName(RandomStringUtils.random(5));
        city2.setAuthor(user);

        when(cityService.getCities()).thenReturn(List.of(city1, city2));

        mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(city1.getId()))
            .andExpect(jsonPath("$[0].name").value(city1.getName()))
            .andExpect(jsonPath("$[0].author.username").value(city1.getAuthor().getUsername()))
            .andExpect(jsonPath("$[1].id").value(city2.getId()))
            .andExpect(jsonPath("$[1].name").value(city2.getName()))
            .andExpect(jsonPath("$[1].author.username").value(city2.getAuthor().getUsername()));
    }

    @Test
    public void updateCityTest() throws Exception {

        CityDto request = new CityDto();
        request.setId(1L);
        request.setName(RandomStringUtils.random(6));

        doNothing().when(cityService).updateCity(request.getId(), request.getName());

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateCityWithEmptyNameTest() throws Exception {

        CityDto request = new CityDto();
        request.setId(1L);
        request.setName("");

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBrandTest() throws Exception {

        doNothing().when(cityService).deleteCity(1L);

        mockMvc.perform(delete(URL + "/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}
