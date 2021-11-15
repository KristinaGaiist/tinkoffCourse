package tinkoff.unit11.controller;

import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tinkoff.unit11.controller.contracts.ModelDto;
import tinkoff.unit11.entity.Brand;
import tinkoff.unit11.entity.Model;
import tinkoff.unit11.service.ModelService;

@Validated
@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ModelController(@Autowired ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping()
    public ResponseEntity<ModelDto[]> getModels() {

        List<Model> modelList = modelService.getModels();
        ModelDto[] modeDtos = modelList.stream()
            .map(model -> modelMapper.map(model, ModelDto.class))
            .toArray(ModelDto[]::new);

        return ResponseEntity.ok(modeDtos);
    }

    @PostMapping()
    public ResponseEntity<Void> createModel(@Valid @RequestBody ModelDto modelDto) {

        Brand brand = modelMapper.map(modelDto.getBrand(), Brand.class);
        modelService.createModel(modelDto.getModel(), brand);

        return ResponseEntity.noContent().build();

    }

    @PutMapping()
    public ResponseEntity<Void> updateModel(@Valid @RequestBody ModelDto modelDto) {

        Brand brand = modelMapper.map(modelDto.getBrand(), Brand.class);
        modelService.updateModel(modelDto.getId(), modelDto.getModel(), brand);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable long id) {

        modelService.deleteModel(id);

        return ResponseEntity.noContent().build();
    }
}
