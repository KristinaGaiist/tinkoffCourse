package unit11.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import unit11.controller.contracts.BrandDto;
import unit11.entity.Brand;
import unit11.service.BrandService;

@Validated
@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;
    private final ModelMapper modelMapper = new ModelMapper();

    public BrandController(@Autowired BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public ResponseEntity<BrandDto[]> getBrands() {

        List<Brand> brandList = brandService.getBrands();

        BrandDto[] brandDtos = brandList.stream()
            .map(brand -> modelMapper.map(brand, BrandDto.class))
            .toArray(BrandDto[]::new);

        return ResponseEntity.ok(brandDtos);
    }

    @PostMapping()
    public ResponseEntity<Void> createBrand(@RequestBody BrandDto brandDto) {

        brandService.createBrand(brandDto.getName());
        return ResponseEntity.noContent().build();

    }

    @PutMapping()
    public ResponseEntity<Void> updateBrand(@RequestBody BrandDto brandDto) {

        brandService.updateBrand(brandDto.getId(), brandDto.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBrand(@PathVariable long id) {

        try {
            brandService.deleteBrand(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Object());
        }
    }
}
