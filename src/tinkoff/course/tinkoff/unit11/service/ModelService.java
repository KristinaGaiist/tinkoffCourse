package tinkoff.unit11.service;

import java.util.List;
import org.springframework.stereotype.Service;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.entity.User;
import tinkoff.unit11.infrastructure.IUserAccessor;
import tinkoff.unit11.repository.BrandRepository;
import tinkoff.unit11.repository.ModelRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class ModelService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final IUserAccessor userAccessor;
    private final PermissionGuard permissionGuard;

    public ModelService(ModelRepository modelRepository, BrandRepository brandRepository,
                        IUserAccessor userAccessor, PermissionGuard permissionGuard) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.userAccessor = userAccessor;
        this.permissionGuard = permissionGuard;
    }

    public List<Model> getModels() {
        return modelRepository.findAll();
    }

    public void createModel(String model, Brand brand) {
        if (modelRepository.findByModel(model) != null) {
            throw new ValidationException(Message.MODEL_ALREADY_EXIST);
        }

        Brand brandEntity = brandRepository.findById(brand.getId())
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));

        User user = userAccessor.getCurrentUser();
        Model newModel = new Model();
        newModel.setModel(model);
        newModel.setBrand(brandEntity);
        newModel.setAuthor(user);

        modelRepository.save(newModel);
    }

    public void updateModel(Long id, String modelName, Brand brand) {
        Model model = modelRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(model.getAuthor());
        Brand brandEntity = brandRepository.findById(brand.getId())
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));

        model.setModel(modelName);
        model.setBrand(brandEntity);

        modelRepository.save(model);
    }

    public void deleteModel(Long id) {
        Model model = modelRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
        permissionGuard.ensureCanModify(model.getAuthor());

        modelRepository.deleteById(id);
    }
}
