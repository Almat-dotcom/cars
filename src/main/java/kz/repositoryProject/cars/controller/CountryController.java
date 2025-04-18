package kz.repositoryProject.cars.controller;

import kz.repositoryProject.cars.entity.Country;
import kz.repositoryProject.cars.repository.CountryRepository;
import kz.repositoryProject.cars.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/addcountry")
    public String addCountry() {
        return "add-country";
    }

    @PostMapping("/addcountry")
    public String addCountry(Country country) {
        countryService.addCountry(country);
        return "redirect:/";
    }
}
