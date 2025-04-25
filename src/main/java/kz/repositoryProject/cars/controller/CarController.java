package kz.repositoryProject.cars.controller;

import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.entity.Country;
import kz.repositoryProject.cars.service.CarService;
import kz.repositoryProject.cars.service.CategoryService;
import kz.repositoryProject.cars.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final CountryService countryService;
    private final CategoryService categoryService;

    @GetMapping(value = "/addcar")
    public String addCarPage(Model model) {
        model.addAttribute("countries", countryService.getAll());
        return "add-car";
    }

    @PostMapping(value = "/addcar")
    public String addCar(@RequestParam(name = "car_name") String name,
                         @RequestParam(name = "car_model") String model,
                         @RequestParam(name = "car_year") int year,
                         @RequestParam(name = "car_price") int price,
                         @RequestParam(name = "country_id") Long countryId) {

        Country country = countryService.getById(countryId);
        Car car = new Car();
        car.setName(name);
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setCountry(country);
        carService.addCar(car);
        return "redirect:/";  // После успешного добавления в машины в базу даннных пользователь будет перенаправлен на главную страницу (о ней чуть позже рааскажу)
    }

    @GetMapping(value = "/")
    public String cars(Model model,
                       @RequestParam(name = "min_year", required = false) Integer year,
                       @RequestParam(name = "car_name", required = false) String name,
                       @RequestParam(name = "max_price", required = false) Integer maxPrice,
                       @RequestParam(name = "country_id", required = false) Long countryId,
                       @RequestParam(name = "category_id", required = false) Long categoryId
                       ) {
        model.addAttribute("countries", countryService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        List<Car> carsPage=carService.findByCriterias(year, name,maxPrice,countryId, categoryId);
        model.addAttribute("cars", carsPage);

        return "cars";
    }

    @GetMapping(value = "car")
    public String getCar(@RequestParam(value = "id") Long id, Model model) {
        Car car = carService.getCarById(id);
        if (car != null) {
            var categories = categoryService.getNotAssignedCategories(car);
            model.addAttribute("car", car);
            model.addAttribute("categories", categories);
            return "car-details";
        } else {
            return "redirect:/404";
        }
    }

    @GetMapping(value = "/404")
    public String carNotFoundPage() {
        return "404";  // Страница ошибки 404
    }

    @GetMapping(value = "updatecar")
    public String editCar(@RequestParam(value = "id") Long id, Model model) {
        Car car = carService.getCarById(id);
        if (car != null) {
            model.addAttribute("car", car);
            model.addAttribute("countries", countryService.getAll());
            return "edit-car";
        } else {
            return "redirect:/404";
        }
    }

    @PostMapping(value = "/updatecar")
    public String updateCar(Car car) {

        if (car != null) {
            carService.updateCar(car);

            // После успешного сохранения изменений пользователь будет перенаправлен на страницу детального просмотра данных машины
            // Передаем дополнительный параметр success, чтобы на странице детального просмотра данных машины отобразилось сообщение об успешном обновлении данных
            return "redirect:/car?id=" + car.getId() + "&success";
        } else {
            // Будет перенаправление на страницу ошибки 404, если машина не будет найдена в базе данных (например, в параметр запроса было передано ошибочное значение идентификатора)
            return "redirect:/404";
        }
    }

    @PostMapping(value = "/deletecar")
    public String deleteCarById(@RequestParam(name = "id") Long id) {
        carService.deleteCarById(id);
        return "redirect:/";
    }

    @GetMapping(value = "search")
    public String searchByName(@RequestParam(name = "name") String name,
                               Model model) {
        if (name.isEmpty()) {
            model.addAttribute("cars", carService.getCars());
        } else {
            model.addAttribute("cars", carService.findByName(name));
        }
        return "cars";
    }

    @PostMapping("/assigncategory")
    public String assignCategory(@RequestParam(name = "category_id") Long categoryId,
                                 @RequestParam(name = "car_id") Long carId) {
        carService.assignCategory(carId, categoryId);
        return "redirect:/car?id=" + carId;
    }

    @PostMapping("/unassigncategory")
    public String unAssignCategory(@RequestParam(name = "category_id") Long categoryId,
                                   @RequestParam(name = "car_id") Long carId) {
        carService.unAssignCategory(carId, categoryId);
        return "redirect:/car?id=" + carId;
    }
}
