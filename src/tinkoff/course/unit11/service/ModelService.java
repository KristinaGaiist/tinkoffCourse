package unit11.service;

import java.util.List;
import org.springframework.stereotype.Service;
import unit11.entity.Brand;
import unit11.entity.Model;
import unit11.repository.BrandRepository;
import unit11.repository.ModelRepository;
import unit8.data.Message;
import unit8.exception.ValidationException;

@Service
public class ModelService {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;

    public ModelService(ModelRepository modelRepository, BrandRepository brandRepository) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
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

        Model newModel = new Model();
        newModel.setModel(model);
        newModel.setBrand(brandEntity);

        modelRepository.save(newModel);
    }

    public void updateModel(Long id, String modelName, Brand brand) {
        Model model = modelRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
        Brand brandEntity = brandRepository.findById(brand.getId())
            .orElseThrow(() -> new ValidationException(Message.BRAND_DOES_NOT_EXIST));

        model.setModel(modelName);
        model.setBrand(brandEntity);

        modelRepository.save(model);
    }

    public void deleteModel(Long id) {
        modelRepository.findById(id)
            .orElseThrow(() ->  new ValidationException(Message.MODEL_DOES_NOT_EXIST));

        modelRepository.deleteById(id);
    }
}
