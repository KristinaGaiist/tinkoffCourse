package tinkoff.unit11.service;

import java.util.List;
import org.springframework.stereotype.Service;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.BrandRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class BrandService {

    private final BrandRepository repository;
    private final IUserAccessor userAccessor;
    private final PermissionGuard permissionGuard;

    public BrandService(BrandRepository repository, IUserAccessor userAccessor,
                        PermissionGuard permissionGuard) {
        this.repository = repository;
        this.userAccessor = userAccessor;
        this.permissionGuard = permissionGuard;
    }

    public List<Brand> getBrands() {
        return repository.findAll();
    }

    public void createBrand(String name) {
        if (repository.findByName(name) != null) {
            throw new ValidationException(Message.BRAND_ALREADY_EXIST);
        }

        User user = userAccessor.getCurrentUser();
        Brand newBrand = new Brand();
        newBrand.setName(name);
        newBrand.setAuthor(user);
        repository.save(newBrand);
    }

    public void updateBrand(Long id, String name) {
        Brand brand = repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(brand.getAuthor());

        brand.setName(name);
        repository.save(brand);
    }

    public void deleteBrand(long id) {
        Brand brand = repository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(brand.getAuthor());

        repository.deleteById(id);
    }
}
