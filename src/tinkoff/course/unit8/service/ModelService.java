package unit8.service;

import java.util.List;
import java.util.Objects;
import unit8.data.Message;
import unit8.entity.Brand;
import unit8.entity.Model;
import unit8.exception.ValidationException;
import unit8.repository.ModelRepository;

public class ModelService {

    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    public Model getModel(String model) {
        return getModelWithValidation(model);
    }

    public Model getModelById(long id) {
        return modelRepository.findById(id)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
    }

    public void createModel(String model, Brand brand) {
        modelRepository.findByModel(model)
            .ifPresent(Model -> {
                throw new ValidationException(Message.MODEL_ALREADY_EXIST);
            });

        Model modelObject = new Model();
        modelObject.setModel(model);
        modelObject.setBrand(brand);

        modelRepository.add(modelObject);
    }

    public void updateModel(String oldModel, String newModel) {
        Model model = getModelWithValidation(oldModel);
        modelRepository.findByModel(newModel)
            .ifPresent(Model -> {
                if (!Objects.equals(Model.getId(), model.getId())) {
                    throw new ValidationException(Message.MODEL_ALREADY_EXIST);
                }
            });
        modelRepository.updateModel(model.getId(), newModel);
    }

    public void deleteModel(String model) {
        getModelWithValidation(model);
        modelRepository.deleteByModel(model);
    }

    private Model getModelWithValidation(String model) {
        return modelRepository.findByModel(model)
            .orElseThrow(() -> new ValidationException(Message.MODEL_DOES_NOT_EXIST));
    }
}
