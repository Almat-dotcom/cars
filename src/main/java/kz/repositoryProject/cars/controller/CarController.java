package kz.repositoryProject.cars.controller;

import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.repository.CarRepository;
import kz.repositoryProject.cars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping(value = "/addcar")
    public String addCarPage() {
        return "add-car";
    }

    @PostMapping(value = "/addcar")
    public String addCar(@RequestParam(name = "car_name") String name,
                         @RequestParam(name = "car_model") String model,
                         @RequestParam(name = "car_year") int year,
                         @RequestParam(name = "car_price") int price) {

        Car car = new Car();
        car.setName(name);
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        carService.addCar(car);
        return "redirect:/";  // После успешного добавления в машины в базу даннных пользователь будет перенаправлен на главную страницу (о ней чуть позже рааскажу)
    }

    @GetMapping(value = "/")
    public String cars(Model model) {
        model.addAttribute("cars", carService.getCars());
        return "cars";
    }

    @GetMapping(value = "car")
    public String getCar(@RequestParam(value = "id") Long id, Model model) {
        Car car = carService.getCarById(id);
        if (car != null) {
            model.addAttribute("car", car);
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
            return "edit-car";
        } else {
            return "redirect:/404";
        }
    }

    @PostMapping(value = "/updatecar")
    public String updateCar(@RequestParam(name = "car_id") Long id,
                            @RequestParam(name = "car_name") String name,
                            @RequestParam(name = "car_model") String model,
                            @RequestParam(name = "car_year") int year,
                            @RequestParam(name = "car_price") int price) {
        Car car = carService.getCarById(id);

        if (car != null) {
            car.setName(name);
            car.setModel(model);
            car.setYear(year);
            car.setPrice(price);
            carService.updateCar(car);

            // После успешного сохранения изменений пользователь будет перенаправлен на страницу детального просмотра данных машины
            // Передаем дополнительный параметр success, чтобы на странице детального просмотра данных машины отобразилось сообщение об успешном обновлении данных
            return "redirect:/car?id=" + id + "&success";
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
}
