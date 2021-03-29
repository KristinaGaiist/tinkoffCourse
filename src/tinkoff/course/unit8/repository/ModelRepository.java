package unit8.repository;

import java.util.List;
import java.util.Optional;
import unit8.entity.Model;

public interface ModelRepository {

    void add(Model model);

    List<Model> findAll();

    Optional<Model> findByModel(String model);

    Optional<Model> findById(long id);

    void updateModel(long id, String newModel);

    void deleteByModel(String model);
}