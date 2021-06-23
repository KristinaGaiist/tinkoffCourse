package unit11.controller;

import java.util.List;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unit11.controller.contracts.CarDto;
import unit11.controller.contracts.CustomerDto;
import unit11.entity.Car;
import unit11.entity.Customer;
import unit11.entity.Model;
import unit11.service.CarService;
import unit11.service.CustomerService;

@Validated
@RestController
@RequestMapping("/cars")
@PreAuthorize("hasAuthority('ADMIN')")
public class CarController {

    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper = new ModelMapper();

    public CarController(@Autowired CarService carService, CustomerService customerService) {
        this.carService = carService;
        this.customerService = customerService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CarDto[]> getCars() {

        List<Car> customerList = carService.getCars();
        CarDto[] customerDtos = customerList.stream()
            .map(car -> modelMapper.map(car, CarDto.class))
            .toArray(CarDto[]::new);

        return ResponseEntity.ok(customerDtos);
    }

    @PostMapping()
    public ResponseEntity<Void> createCar(@RequestBody CarDto carDto) {

        Model model = modelMapper.map(carDto.getModel(), Model.class);
        carService.createCar(carDto.getStateNumber(), model);

        return ResponseEntity.noContent().build();

    }

    @PutMapping()
    public ResponseEntity<Void> updateCar(@RequestBody CarDto carDto) {

        carService.updateCarStateNumber(carDto.getId(), carDto.getStateNumber());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {

        carService.deleteCar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{carId}/customers")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CustomerDto[]> getCustomers(@PathVariable("carId") long carId) {

        // TODO: Move getCarCustomers to carsService
        Set<Customer> customerSet = customerService.getCustomersByCarId(carId);
        CustomerDto[] customerDtos = customerSet.stream()
            .map(customer -> modelMapper.map(customer, CustomerDto.class))
            .toArray(CustomerDto[]::new);

        return ResponseEntity.ok(customerDtos);
    }

    @PostMapping("/{carId}/customers/{customerId}")
    public ResponseEntity<Void> addCustomer(@PathVariable("carId") long carId,
                                            @PathVariable("customerId") long customerId) {
        carService.addCustomer(carId, customerId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{carId}/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("carId") long carId,
                                               @PathVariable("customerId") long customerId) {
        carService.deleteCustomer(carId, customerId);

        return ResponseEntity.noContent().build();
    }
}
