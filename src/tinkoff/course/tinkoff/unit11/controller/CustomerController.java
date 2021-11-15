package tinkoff.unit11.controller;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tinkoff.unit11.controller.contracts.CarDto;
import tinkoff.unit11.controller.contracts.CustomerDto;
import tinkoff.unit11.entity.Car;
import tinkoff.unit11.entity.City;
import tinkoff.unit11.entity.Customer;
import tinkoff.unit11.service.CarService;
import tinkoff.unit11.service.CustomerService;

@Validated
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CarService carService;
    private final ModelMapper modelMapper = new ModelMapper();

    public CustomerController(@Autowired CustomerService customerService, CarService carService) {
        this.customerService = customerService;
        this.carService = carService;
    }

    @GetMapping()
    public ResponseEntity<CustomerDto[]> getCustomers() {

        List<Customer> customerList = customerService.getCustomers();
        CustomerDto[] customerDtos = customerList.stream()
            .map(customer -> modelMapper.map(customer, CustomerDto.class))
            .toArray(CustomerDto[]::new);

        return ResponseEntity.ok(customerDtos);
    }

    @PostMapping()
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerDto customerDto) {

        City city = modelMapper.map(customerDto.getCity(), City.class);
        customerService.createCustomer(customerDto.getFirstName(),
                                       customerDto.getLastName(),
                                       customerDto.getMiddleName(),
                                       city);

        return ResponseEntity.noContent().build();

    }

    @PutMapping()
    public ResponseEntity<Void> updateCustomer(@Valid @RequestBody CustomerDto customerDto) {

        City city = modelMapper.map(customerDto.getCity(), City.class);
        customerService.updateCustomerInfo(customerDto.getId(),
                                           customerDto.getFirstName(),
                                           customerDto.getLastName(),
                                           customerDto.getMiddleName(),
                                           city);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{customerId}/cars")
    public ResponseEntity<CarDto[]> getCars(@PathVariable("customerId") long customerId) {

        Set<Car> carSet = carService.getCarsByCustomerId(customerId);
        CarDto[] carDtos = carSet.stream()
            .map(car -> modelMapper.map(car, CarDto.class))
            .toArray(CarDto[]::new);

        return ResponseEntity.ok(carDtos);
    }

    @PostMapping("/{customerId}/cars/{carId}")
    public ResponseEntity<Void> addCar(@PathVariable("customerId") long customerId,
                                       @PathVariable("carId") long carId) {
        customerService.addCar(customerId, carId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/cars/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable("customerId") long customerId,
                                          @PathVariable("carId") long carId) {
        customerService.deleteCar(customerId, carId);

        return ResponseEntity.noContent().build();
    }
}
