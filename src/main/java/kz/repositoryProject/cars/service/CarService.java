package kz.repositoryProject.cars.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.entity.Category;
import kz.repositoryProject.cars.entity.Country;
import kz.repositoryProject.cars.repository.CarRepository;
import kz.repositoryProject.cars.repository.custom.CustomCarRepository;
import kz.repositoryProject.cars.util.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;
    private final CategoryService categoryService;
    private final CustomCarRepository customCarRepository;

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> findByCriterias(Integer year, String name, Integer maxPrice, Long countryId, Long categoryId) {
        List<Car> cars = new ArrayList<>();
//        if (year != null && name != null) {
//            cars = carRepository.findAllByYearAndName(year, name);
//        } else if (year != null) {
//            cars = carRepository.findAllByYear(year);
//        } else if (name != null) {
//            cars = carRepository.findAllByName(name);
//        } else {
//            cars = carRepository.findAll();
//        }
//        if (year == null && (name == null || name.isEmpty())) {
//            return carRepository.findAll();
//        } else if (year == null) {
//            return carRepository.findAllByName(name);
//        } else if (name == null || name.isEmpty()) {
//            return carRepository.findAllByYear(year);
//        } else {
//            return carRepository.findAllByYearAndName(year, name);
//        }
//        return customCarRepository.findAllByCriteria(year, name,maxPrice, countryId, categoryId);
//        return carRepository.findAllBySearch(year, name);
        return carRepository.findAll(CarSpecification.getCarSpecification(year, name, maxPrice, countryId, categoryId));
    }

    public void updateCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> findByName(String name) {
        return carRepository.findAllByNameContainingIgnoreCase(name);
    }

    public void assignCategory(Long carId, Long categoryId) {
        Car car = carRepository.findById(carId).orElse(null);
        Category category = categoryService.findById(categoryId);

        if (car != null && category != null) {
            car.getCategories().add(category);
        }

        updateCar(car);
    }

    public void unAssignCategory(Long carId, Long categoryId) {
        Car car = carRepository.findById(carId).orElse(null);
        Category category = categoryService.findById(categoryId);

        if (car != null && category != null) {
            car.getCategories().remove(category);
            carRepository.save(car);
        }
    }
}
