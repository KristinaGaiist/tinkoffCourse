package unit11.service;

import java.util.List;
import org.springframework.stereotype.Service;
import unit11.entity.Brand;
import unit11.repository.BrandRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class BrandService {

    private final BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    public List<Brand> getBrands() {
        return repository.findAll();
    }

    public void createBrand(String name) {
        if (repository.findByName(name) != null) {
            throw new ValidationException(Message.BRAND_ALREADY_EXIST);
        }

        Brand newBrand = new Brand();
        newBrand.setName(name);
        repository.save(newBrand);
    }

    public void updateBrand(Long id, String name) {
        Brand brand = repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));

        brand.setName(name);
        repository.save(brand);
    }

    public void deleteBrand(long id) {
        repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));

        repository.deleteById(id);
    }
}
