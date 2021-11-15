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
import tinkoff.unit11.controller.CustomerController;
import tinkoff.unit11.controller.contracts.CityDto;
import tinkoff.unit11.controller.contracts.CustomerDto;
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
public class CustomerControllerTest {

    private static final String URL = "/customers";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public CustomerController customerController;

    @MockBean
    public CustomerService customerService;
    @MockBean
    public CarService carService;

    @Test
    public void createCustomerTest() throws Exception {

        CustomerDto request = new CustomerDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setFirstName(RandomStringUtils.random(5));
        request.setLastName(RandomStringUtils.random(5));
        request.setMiddleName(RandomStringUtils.random(5));
        request.setCity(createCity());

        doNothing()
            .when(customerService)
            .createCustomer(request.getFirstName(), request.getLastName(), request.getMiddleName(),
                            convertCityDtoToCity(request.getCity()));

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void createCustomerWithEmptyFieldsTest() throws Exception {

        CustomerDto request = new CustomerDto();
        request.setId(1L);
        request.setAuthor(new UserDto());

        mockMvc.perform(post(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getCustomersTest() throws Exception {

        User user = new User();
        user.setUsername(RandomStringUtils.random(6));

        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setAuthor(user);
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));
        customer.setCity(convertCityDtoToCity(createCity()));

        when(customerService.getCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(get(URL)
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
    public void updateCustomerTest() throws Exception {

        CustomerDto request = new CustomerDto();
        request.setId(1L);
        request.setAuthor(new UserDto());
        request.setFirstName(RandomStringUtils.random(5));
        request.setLastName(RandomStringUtils.random(5));
        request.setMiddleName(RandomStringUtils.random(5));
        request.setCity(createCity());

        doNothing()
            .when(customerService)
            .updateCustomerInfo(request.getId(), request.getFirstName(), request.getLastName(), request.getMiddleName(),
                                convertCityDtoToCity(request.getCity()));

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void updateCustomerWithEmptyFieldsTest() throws Exception {

        CustomerDto request = new CustomerDto();
        request.setId(1L);
        request.setAuthor(new UserDto());

        mockMvc.perform(put(URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(TestUtil.asJsonString(request))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCustomerTest() throws Exception {

        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete(URL + "/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void getCarsTest() throws Exception {

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

        Brand brand = new Brand();
        brand.setName(RandomStringUtils.random(5));

        Model model = new Model();
        model.setBrand(brand);
        model.setModel(RandomStringUtils.random(5));

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));
        car.setAuthor(user);
        car.setModel(model);

        car.setCustomers(Set.of(customer));
        customer.setCars(Set.of(car));

        when(carService.getCarsByCustomerId(customer.getId())).thenReturn(Set.of(car));

        mockMvc.perform(get(URL + "/{customerId}/cars", customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(car.getId()))
            .andExpect(jsonPath("$[0].stateNumber").value(car.getStateNumber()))
            .andExpect(jsonPath("$[0].author.username").value(car.getAuthor().getUsername()))
            .andExpect(jsonPath("$[0].model.model").value(car.getModel().getModel()))
            .andExpect(jsonPath("$[0].model.brand.name").value(car.getModel().getBrand().getName()));
    }

    @Test
    public void addCarTest() throws Exception {

        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));

        doNothing().when(customerService).addCar(customer.getId(), car.getId());

        mockMvc.perform(delete(URL + "/{customerId}/cars/{carId}", car.getId(), customer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCarTest() throws Exception {

        Customer customer = new Customer();
        customer.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        customer.setFirstName(RandomStringUtils.random(5));
        customer.setLastName(RandomStringUtils.random(5));
        customer.setMiddleName(RandomStringUtils.random(5));

        Car car = new Car();
        car.setId(5L);
        car.setStateNumber(RandomStringUtils.random(5));

        doNothing().when(customerService).deleteCar(customer.getId(), car.getId());

        mockMvc.perform(delete(URL + "/{customerId}/cars/{carId}", customer.getId(), car.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    private CityDto createCity() {
        CityDto city = new CityDto();
        city.setId(5L);
        city.setName(RandomStringUtils.random(7));

        return city;
    }

    private City convertCityDtoToCity(CityDto cityDto) {
        City city = new City();
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());

        return city;
    }
}
