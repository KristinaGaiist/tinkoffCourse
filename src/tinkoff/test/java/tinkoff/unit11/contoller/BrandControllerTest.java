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
import tinkoff.unit11.controller.BrandController;
import tinkoff.unit11.controller.contracts.BrandDto;
import tinkoff.unit11.controller.contracts.UserDto;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.service.BrandService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class BrandControllerTest {

    private static final String URL = "/brands";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public BrandController brandController;

    @MockBean
    public BrandService brandService;

    @Test
    public void createBrandTest() throws Exception {

        BrandDto request = new BrandDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setName(RandomStringUtils.random(5));

        doNothing().when(brandService).createBrand(request.getName());

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void createBrandWithEmptyNameTest() throws Exception {

        BrandDto request = new BrandDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setName("");

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getBrandsTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(6));

        Brand brand1 = new Brand();
        brand1.setId(5L);
        brand1.setName(RandomStringUtils.random(5));
        brand1.setAuthor(user);

        Brand brand2 = new Brand();
        brand2.setId(15L);
        brand2.setName(RandomStringUtils.random(5));
        brand2.setAuthor(user);

        when(brandService.getBrands()).thenReturn(List.of(brand1, brand2));

        mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(brand1.getId()))
            .andExpect(jsonPath("$[0].name").value(brand1.getName()))
            .andExpect(jsonPath("$[0].author.username").value(brand1.getAuthor().getUsername()))
            .andExpect(jsonPath("$[1].id").value(brand2.getId()))
            .andExpect(jsonPath("$[1].name").value(brand2.getName()))
            .andExpect(jsonPath("$[1].author.username").value(brand2.getAuthor().getUsername()));
    }

    @Test
    public void updateBrandTest() throws Exception {

        BrandDto request = new BrandDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setName(RandomStringUtils.random(6));

        doNothing().when(brandService).updateBrand(request.getId(), request.getName());

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateBrandWithEmptyNameTest() throws Exception {

        BrandDto request = new BrandDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setName("");

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBrandTest() throws Exception {

        doNothing().when(brandService).deleteBrand(1L);

        mockMvc.perform(delete(URL + "/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }
}
