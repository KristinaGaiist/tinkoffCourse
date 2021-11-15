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
import java.util.Set;
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
import tinkoff.unit11.controller.CarController;
import tinkoff.unit11.controller.contracts.BrandDto;
import tinkoff.unit11.controller.contracts.CarDto;
import tinkoff.unit11.controller.contracts.ModelDto;
import tinkoff.unit11.controller.contracts.UserDto;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Car;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.service.CarService;
import tinkoff.unit11.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "test", password = "test", roles = "USER")
public class CarControllerTest {

    private static final String URL = "/cars";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public CarController carController;

    @MockBean
    public CarService carService;
    @MockBean
    public CustomerService customerService;

    @Test
    public void createCarTest() throws Exception {

        CarDto request = new CarDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setStateNumber(RandomStringUtils.random(5));
        request.setModel(createModel());

        doNothing().when(carService).createCar(request.getStateNumber(), convertModelDtoToModel(request.getModel()));

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void createCarWithEmptyStateNumberTest() throws Exception {

        CarDto request = new CarDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setModel(createModel());

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getCarsTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(6));

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));
        car.setAuthor(user);
        car.setModel(convertModelDtoToModel(createModel()));

        Car car2 = new Car();
        car2.setId(50L);
        car2.setStateNumber(RandomStringUtils.random(5));
        car2.setAuthor(user);
        car2.setModel(convertModelDtoToModel(createModel()));

        when(carService.getCars()).thenReturn(List.of(car, car2));

        mockMvc.perform(get(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(car.getId()))
            .andExpect(jsonPath("$[0].stateNumber").value(car.getStateNumber()))
            .andExpect(jsonPath("$[0].author.username").value(car.getAuthor().getUsername()))
            .andExpect(jsonPath("$[0].model.model").value(car.getModel().getModel()))
            .andExpect(jsonPath("$[0].model.brand.name").value(car.getModel().getBrand().getName()))
            .andExpect(jsonPath("$[1].id").value(car2.getId()))
            .andExpect(jsonPath("$[1].stateNumber").value(car2.getStateNumber()))
            .andExpect(jsonPath("$[1].author.username").value(car2.getAuthor().getUsername()))
            .andExpect(jsonPath("$[1].model.model").value(car2.getModel().getModel()))
            .andExpect(jsonPath("$[1].model.brand.name").value(car2.getModel().getBrand().getName()));
    }

    @Test
    public void updateCarTest() throws Exception {

        CarDto request = new CarDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setStateNumber(RandomStringUtils.random(5));
        request.setModel(createModel());

        doNothing().when(carService).updateCarStateNumber(request.getId(), request.getStateNumber());

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateCarWithEmptyStateNumberTest() throws Exception {

        CarDto request = new CarDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setStateNumber("");
        request.setModel(createModel());

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCarTest() throws Exception {

        doNothing().when(carService).deleteCar(1L);

        mockMvc.perform(delete(URL + "/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void getCustomersTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(6));

        City city = new City();
        city.setId(1L);
        city.setName(RandomStringUtils.random(2));

        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setAuthor(user);
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));
        customer.setCity(city);

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));
        car.setAuthor(user);
        car.setModel(convertModelDtoToModel(createModel()));

        car.setCustomers(Set.of(customer));
        customer.setCars(Set.of(car));

        when(customerService.getCustomersByCarId(car.getId())).thenReturn(Set.of(customer));

        mockMvc.perform(get(URL + "/{carId}/customers", 5L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(customer.getId()))
            .andExpect(jsonPath("$[0].firstName").value(customer.getFirstName()))
            .andExpect(jsonPath("$[0].lastName").value(customer.getLastName()))
            .andExpect(jsonPath("$[0].middleName").value(customer.getMiddleName()))
            .andExpect(jsonPath("$[0].city.id").value(customer.getCity().getId()))
            .andExpect(jsonPath("$[0].city.name").value(customer.getCity().getName()))
            .andExpect(jsonPath("$[0].author.username").value(customer.getAuthor().getUsername()));
    }

    @Test
    public void addCustomerTest() throws Exception {

        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));
        car.setModel(convertModelDtoToModel(createModel()));

        doNothing().when(carService).addCustomer(car.getId(), customer.getId());

        mockMvc.perform(delete(URL + "/{carId}/customers/{customerId}", car.getId(), customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCustomerTest() throws Exception {

        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));
        car.setModel(convertModelDtoToModel(createModel()));

        doNothing().when(carService).deleteCustomer(car.getId(), customer.getId());

        mockMvc.perform(delete(URL + "/{carId}/customers/{customerId}", car.getId(), customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    private ModelDto createModel() {
        BrandDto brand = new BrandDto();
        brand.setId(1L);
        brand.setAuthor(new UserDto());
        brand.setName(RandomStringUtils.random(6));

        ModelDto model = new ModelDto();
        model.setId(5L);
        model.setAuthor(new UserDto());
        model.setBrand(brand);
        model.setModel(RandomStringUtils.random(6));

        return model;
    }

    private Model convertModelDtoToModel(ModelDto modelDto) {
        Brand brand = new Brand();
        brand.setId(modelDto.getBrand().getId());
        brand.setName(modelDto.getBrand().getName());

        Model model = new Model();
        model.setId(modelDto.getId());
        model.setBrand(brand);
        model.setModel(model.getModel());

        return model;
    }
}
