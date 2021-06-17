package unit8.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unit8.data.Message;
import unit8.entity.Brand;
import unit8.exception.ValidationException;
import unit8.repository.BrandRepository;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(@Autowired BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAllBrands();
    }

    public Brand getBrandByName(String brandName) {
        return brandRepository.findByName(brandName)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));
    }

    public Brand getBrandById(long id) {
        return brandRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));
    }

    public void createBrand(String brandName) {
        if (brandRepository.existsByName(brandName)) {
            throw new ValidationException(Message.BRAND_ALREADY_EXIST);
        }
        brandRepository.addBrand(brandName);
    }

    public void renameBrand(String oldName, String newName) {
        Brand brand = brandRepository.findByName(oldName)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));
        if (brandRepository.existsByName(newName, brand.getId())) {
            throw new ValidationException(Message.BRAND_ALREADY_EXIST);
        }

        brandRepository.updateBrand(brand.getId(), newName);
    }

    public void deleteBrand(String brandName) {
        if (!brandRepository.existsByName(brandName)) {
            throw new ValidationException(Message.BRAND_DOES_NOT_EXIST);
        }
        brandRepository.deleteBrandByName(brandName);
    }
}
