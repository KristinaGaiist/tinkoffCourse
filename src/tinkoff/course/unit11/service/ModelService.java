package unit11.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import unit11.entity.Brand;
import unit11.entity.Model;
import unit11.repository.BrandRepository;
import unit11.repository.ModelRepository;

@Service
public class ModelService {

    private final ModelRepository repository;

    public ModelService(ModelRepository repository) {
        this.repository = repository;
    }

    public List<Model> getModels() {
        return repository.findAll();
    }

    public void createModel(String model) {
        Model newModel = new Model();
        newModel.setModel(model);
        repository.save(newModel);
    }

    public void updateModel(Long id, String model) {
        Optional<Model> brand = repository.findById(id);
        brand.ifPresent(m -> {
            m.setModel(model);
            repository.save(m);
        });
    }

    public void deleteModel(Long id) {
        repository.deleteById(id);
    }
}
