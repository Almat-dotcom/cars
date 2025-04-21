package kz.repositoryProject.cars.repository;

import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByCarsNotContaining(Car car);
}
