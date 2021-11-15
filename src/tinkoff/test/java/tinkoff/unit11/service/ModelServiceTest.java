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
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.BrandRepository;
import tinkoff.unit11.repository.ModelRepository;
import unit8.exception.ValidationException;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ModelServiceTest {

    @Autowired
    public ModelService modelService;

    @MockBean
    public ModelRepository modelRepository;
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
    public void createModelTest() {

        Brand brand = createBrand();
        when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));

        String model = RandomStringUtils.random(4);
        modelService.createModel(model, brand);

        ArgumentCaptor<Model> argument = ArgumentCaptor.forClass(Model.class);
        verify(modelRepository, times(1)).save(argument.capture());
        assertThat(model).isEqualTo(argument.getValue().getModel());
        assertThat(brand).isEqualTo(argument.getValue().getBrand());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void createAlreadyExistModelTest() {

        Model model = createModel(createBrand());
        when(modelRepository.findByModel(model.getModel())).thenReturn(model);

        try {
            modelService.createModel(model.getModel(), createBrand());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(modelRepository, never()).save(any(Model.class));
    }

    @Test
    public void createBrandDoesNotExistTest() {

        when(brandRepository.findById(any())).thenReturn(Optional.empty());

        try {
            modelService.createModel(RandomStringUtils.random(6), createBrand());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(modelRepository, never()).save(any(Model.class));
    }

    @Test
    public void getAllModelsTest() {

        Brand brand = createBrand();
        Model model1 = createModel(brand);
        Model model2 = createModel(brand);
        Model model3 = createModel(brand);
        List<Model> models = List.of(model1, model2, model3);

        when(modelRepository.findAll()).thenReturn(models);

        List<Model> result = modelService.getModels();
        assertThat(result).hasSize(models.size());
        assertThat(result).isEqualTo(models);
    }

    @Test
    public void updateModelTest() {

        Brand brand = createBrand();
        Model model = createModel(brand);
        when(modelRepository.findById(model.getId())).thenReturn(Optional.of(model));
        when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));

        String newModel = RandomStringUtils.random(4);
        modelService.updateModel(model.getId(), newModel, brand);

        ArgumentCaptor<Model> argument = ArgumentCaptor.forClass(Model.class);
        verify(modelRepository, times(1)).save(argument.capture());
        assertThat(newModel).isEqualTo(argument.getValue().getModel());
        assertThat(brand).isEqualTo(argument.getValue().getBrand());
        assertThat(user).isEqualTo(argument.getValue().getAuthor());
    }

    @Test
    public void updateDoesNotExistModelTest() {

        Long id = 5L;
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        Brand brand = createBrand();
        String newModel = RandomStringUtils.random(6);

        try {
            modelService.updateModel(id, newModel, brand);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(modelRepository, never()).save(any(Model.class));
    }

    @Test
    public void updateDoesNotExistBrandTest() {

        Model model = createModel(createBrand());
        when(modelRepository.findById(model.getId())).thenReturn(Optional.of(model));
        when(brandRepository.findById(any())).thenReturn(Optional.empty());

        try {
            modelService.updateModel(model.getId(), model.getModel(), new Brand());
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(modelRepository, never()).save(any(Model.class));
    }

    @Test
    public void deleteModelTest() {

        Model model = createModel(createBrand());
        when(modelRepository.findById(model.getId())).thenReturn(Optional.of(model));

        modelService.deleteModel(model.getId());

        verify(modelRepository, times(1)).deleteById(model.getId());
    }

    @Test
    public void deleteDoesNotExistModelTest() {

        long id = 5L;
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        try {
            modelService.deleteModel(id);
            Assertions.fail("Should have exception thrown");
        } catch (ValidationException ignored) {
        }

        verify(modelRepository, never()).save(any(Model.class));
    }

    private Brand createBrand() {
        Brand brand = new Brand();
        brand.setId(5L);
        brand.setName(RandomStringUtils.random(5));
        return brand;
    }

    private Model createModel(Brand brand) {
        Model model = new Model();
        model.setId(Long.parseLong(RandomStringUtils.randomNumeric(4)));
        model.setModel(RandomStringUtils.random(5));
        model.setBrand(brand);
        model.setAuthor(user);

        return model;
    }
}
