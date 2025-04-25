package kz.repositoryProject.cars.repository.custom;

import kz.repositoryProject.cars.entity.Car;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCarRepository {
    List<Car> findAllByCriteria(Integer year, String name, Integer maxPrice, Long countryId, Long categoryId);
}
