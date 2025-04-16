package kz.repositoryProject.cars.service;

import jakarta.transaction.Transactional;
import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
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
}
