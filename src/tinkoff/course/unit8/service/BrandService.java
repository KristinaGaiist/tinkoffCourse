package unit8.service;

import java.util.List;
import java.util.Objects;
import unit8.data.Message;
import unit8.entity.Brand;
import unit8.exception.ValidationException;
import unit8.repository.BrandRepository;

public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
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
        brandRepository.findByName(brandName)
            .ifPresent(brand -> {
                throw new ValidationException(Message.BRAND_ALREADY_EXIST);
            });
        brandRepository.addBrand(brandName);
    }

    public void renameBrand(String oldName, String newName) {
        Brand oldBrand = getBrandByName(oldName);
        brandRepository.findByName(newName)
            .ifPresent(brand -> {
                if (!Objects.equals(brand.getId(), oldBrand.getId())) {
                    throw new ValidationException(Message.BRAND_ALREADY_EXIST);
                }
            });
        brandRepository.updateBrand(oldBrand.getId(), newName);
    }

    public void deleteBrand(String brandName) {
        brandRepository.findByName(brandName)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));

        brandRepository.deleteBrandByName(brandName);
    }
}
