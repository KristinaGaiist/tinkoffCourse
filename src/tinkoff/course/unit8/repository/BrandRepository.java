package unit8.repository;

import java.util.List;
import java.util.Optional;
import unit8.entity.Brand;

public interface BrandRepository {

    void addBrand(String brandName);

    List<Brand> findAllBrands();

    Optional<Brand> findByName(String brandName);

    Optional<Brand> findById(long id);

    void updateBrand(long id, String newName);

    void deleteBrandByName(String brandName);
}