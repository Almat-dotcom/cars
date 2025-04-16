package kz.repositoryProject.cars.repository;

import jakarta.transaction.Transactional;
import kz.repositoryProject.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByNameContainingIgnoreCase(String name);
}
