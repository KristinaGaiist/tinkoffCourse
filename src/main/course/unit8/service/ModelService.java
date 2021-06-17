package unit8.service;

import java.util.List;
import org.springframework.stereotype.Service;
import unit8.data.Message;
import unit8.entity.Brand;
import unit8.entity.Model;
import unit8.exception.ValidationException;
import unit8.repository.ModelRepository;

@Service
public class ModelService {

    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Model getModel(String model) {
        return modelRepository.findByModel(model)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
    }

    public Model getModelById(long id) {
        return modelRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
    }

    public void createModel(String model, Brand brand) {
        if (modelRepository.existsByModel(model)) {
            throw new ValidationException(Message.MODEL_ALREADY_EXIST);
        }

        Model modelObject = new Model();
        modelObject.setModel(model);
        modelObject.setBrand(brand);

        modelRepository.add(modelObject);
    }

    public void updateModel(String oldModel, String newModel) {
        Model model = modelRepository.findByModel(oldModel)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
        if (modelRepository.existsByModel(newModel, model.getId())) {
            throw new ValidationException(Message.MODEL_ALREADY_EXIST);
        }

        modelRepository.updateModel(model.getId(), newModel);
    }

    public void deleteModel(String model) {
        if (!modelRepository.existsByModel(model)) {
            throw new ValidationException(Message.MODEL_DOES_NOT_EXIST);
        }
        modelRepository.deleteByModel(model);
    }
}
