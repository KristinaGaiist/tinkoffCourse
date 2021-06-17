package unit11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import unit8.entity.Car;
import unit8.service.CarService;
import unit8.service.ModelService;

@Controller
public class CarController {
    private final CarService carService;
    private final ModelService modelService;

    public CarController(CarService carService, ModelService modelService) {
        this.carService = carService;
        this.modelService = modelService;
    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "cars";
    }

    @GetMapping("/createCar")
    public String getCreateCar(Model model) {
        model.addAttribute("models", modelService.getAllModels());
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCar(@ModelAttribute("car") Car car) {
        if (car != null) {
            carService.createCar(car.getStateNumber(), car.getModel());
        }
        return "redirect:/cars";
    }
}
