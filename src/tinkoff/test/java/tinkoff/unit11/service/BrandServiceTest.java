package tinkoff.unit11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.BrandRepository;
import unit8.exception.ValidationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BrandServiceTest {

    @Autowired
    public BrandService brandService;

    @MockBean
    public BrandRepository brandRepository;
    @MockBean
    public IUserAccessor userAccessor;
    @MockBean
    public PermissionGuard permissionGuard;

    private final User user = new User();

    @BeforeEach
    public void init() {
        when(userAccessor.getCurrentUser()).thenReturn(user);
        doNothing().when(permissionGuard).ensureCanModify(any(User.class));
    }

    @Test
    public void createBrandTest() {

        String name = RandomStringUtils.random(4);
        brandService.createBrand(name);

        ArgumentCaptor<Brand> argument = ArgumentCaptor.forClass(Brand.class);
        verify(brandRepository, times(1)).save(argument.capture());
        assertThat(name).isEqualTo(argument.getValue().getName());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void createAlreadyExistBrandTest() {

        Brand brand = createBrand();
        when(brandRepository.findByName(brand.getName())).thenReturn(brand);

        try {
            brandService.createBrand(brand.getName());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    public void getAllBrandsTest() {

        Brand brand1 = createBrand();
        Brand brand2 = createBrand();
        Brand brand3 = createBrand();
        List<Brand> brands = List.of(brand1, brand2, brand3);

        when(brandRepository.findAll()).thenReturn(brands);

        List<Brand> result = brandService.getBrands();
        assertThat(result).hasSize(brands.size());
        assertThat(result).isEqualTo(brands);
    }

    @Test
    public void updateBrandTest() {

        Brand brand = createBrand();

        when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));

        String newName = RandomStringUtils.random(4);
        brandService.updateBrand(brand.getId(), newName);

        ArgumentCaptor<Brand> argument = ArgumentCaptor.forClass(Brand.class);
        verify(brandRepository, times(1)).save(argument.capture());
        assertThat(newName).isEqualTo(argument.getValue().getName());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void updateDoesNotExistBrandTest() {

        Long id = 5L;
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        try {
            brandService.updateBrand(id, RandomStringUtils.random(4));
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    public void deleteBrandTest() {

        Brand brand = createBrand();
        when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));

        brandService.deleteBrand(brand.getId());

        verify(brandRepository, times(1)).deleteById(brand.getId());
    }

    @Test
    public void deleteDoesNotExistBrandTest() {

        long id = 5L;
        when(brandRepository.findById(id)).thenReturn(Optional.empty());

        try {
            brandService.deleteBrand(id);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(brandRepository, never()).save(any(Brand.class));
    }

    private Brand createBrand() {
        Brand brand = new Brand();
        brand.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        brand.setName(RandomStringUtils.random(5));
        brand.setAuthor(user);

        return brand;
    }
}
