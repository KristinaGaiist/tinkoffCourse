package unit11.controller;

import java.util.List;
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
import unit11.controller.contracts.CityDto;
import unit11.entity.City;
import unit11.service.CityService;

@Validated
@RestController
@RequestMapping("/cities")
@PreAuthorize("hasAuthority('ADMIN')")
public class CityController {

    private final CityService cityService;
    private final ModelMapper modelMapper = new ModelMapper();

    public CityController(@Autowired CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CityDto[]> getCities() {

        List<City> cityList = cityService.getCities();

        CityDto[] cityDtos = cityList.stream()
            .map(city -> modelMapper.map(city, CityDto.class))
            .toArray(CityDto[]::new);

        return ResponseEntity.ok(cityDtos);
    }

    @PostMapping()
    public ResponseEntity<Void> createCity(@RequestBody CityDto cityDto) {

        cityService.createCity(cityDto.getName());

        return ResponseEntity.noContent().build();

    }

    @PutMapping()
    public ResponseEntity<Void> updateCity(@RequestBody CityDto cityDto) {

        cityService.updateCity(cityDto.getId(), cityDto.getName());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable long id) {

        cityService.deleteCity(id);

        return ResponseEntity.noContent().build();
    }
}
