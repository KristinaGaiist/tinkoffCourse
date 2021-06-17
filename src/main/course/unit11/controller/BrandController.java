package unit11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import unit8.entity.Brand;
import unit8.service.BrandService;

@Controller
public class BrandController {

    private final BrandService service;

    public BrandController(@Autowired BrandService brandService) {
        this.service = brandService;
    }

    @GetMapping("/brands")
    public String getBrands(Model model) {
        model.addAttribute("brands", service.getAllBrands());
        return "brands";
    }

    @PostMapping("/createBrand")
    public String createOrder(@ModelAttribute Brand brand) {
        if (brand != null) {
            service.createBrand(brand.getName());
        }
        return "redirect:/brands";
    }
}
