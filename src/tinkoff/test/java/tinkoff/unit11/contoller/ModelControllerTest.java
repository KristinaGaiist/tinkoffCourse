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
import tinkoff.unit11.controller.ModelController;
import tinkoff.unit11.controller.contracts.BrandDto;
import tinkoff.unit11.controller.contracts.ModelDto;
import tinkoff.unit11.controller.contracts.UserDto;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.service.ModelService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class ModelControllerTest {

    private static final String URL = "/models";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public ModelController modelController;

    @MockBean
    public ModelService modelService;

    @Test
    public void createModelTest() throws Exception {

        ModelDto request = new ModelDto();
        request.setId(5L);
        request.setAuthor(new UserDto());
        request.setBrand(createBrand());
        request.setModel(RandomStringUtils.random(7));

        doNothing().when(modelService).createModel(request.getModel(), convertBrandDtoToBrand(request.getBrand()));

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void createModelWithNullModelTest() throws Exception {

        ModelDto request = new ModelDto();
        request.setId(5L);
        request.setAuthor(new UserDto());
        request.setBrand(createBrand());

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getModelsTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(6));

        Brand brand = new Brand();
        brand.setId(5L);
        brand.setName(RandomStringUtils.random(5));

        Model model1 = new Model();
        model1.setId(5L);
        model1.setAuthor(user);
        model1.setBrand(brand);
        model1.setModel(RandomStringUtils.random(7));

        Model model2 = new Model();
        model2.setId(25L);
        model2.setAuthor(user);
        model2.setBrand(brand);
        model2.setModel(RandomStringUtils.random(7));

        when(modelService.getModels()).thenReturn(List.of(model1, model2));

        mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(model1.getId()))
            .andExpect(jsonPath("$[0].model").value(model1.getModel()))
            .andExpect(jsonPath("$[0].author.username").value(model1.getAuthor().getUsername()))
            .andExpect(jsonPath("$[0].brand.name").value(model1.getBrand().getName()))
            .andExpect(jsonPath("$[1].id").value(model2.getId()))
            .andExpect(jsonPath("$[1].model").value(model2.getModel()))
            .andExpect(jsonPath("$[1].author.username").value(model2.getAuthor().getUsername()))
            .andExpect(jsonPath("$[1].brand.name").value(model1.getBrand().getName()));
    }

    @Test
    public void updateModelTest() throws Exception {

        ModelDto request = new ModelDto();
        request.setId(5L);
        request.setAuthor(new UserDto());
        request.setBrand(createBrand());
        request.setModel(RandomStringUtils.random(6));

        doNothing().when(modelService).updateModel(request.getId(), request.getModel(),
                                                   convertBrandDtoToBrand(request.getBrand()));

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateModelWithNullModelTest() throws Exception {

        ModelDto request = new ModelDto();
        request.setId(5L);
        request.setAuthor(new UserDto());
        request.setBrand(createBrand());

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteModelTest() throws Exception {

        doNothing().when(modelService).deleteModel(1L);

        mockMvc.perform(delete(URL + "/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    private BrandDto createBrand() {
        BrandDto brand = new BrandDto();
        brand.setId(1L);
        brand.setAuthor(new UserDto());
        brand.setName(RandomStringUtils.random(6));

        return brand;
    }

    private Brand convertBrandDtoToBrand(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setId(brandDto.getId());
        brand.setName(brandDto.getName());

        return brand;
    }
}
