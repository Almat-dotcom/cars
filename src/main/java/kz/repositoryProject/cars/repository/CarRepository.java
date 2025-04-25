package kz.repositoryProject.cars.repository;

import jakarta.transaction.Transactional;
import kz.repositoryProject.cars.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByNameContainingIgnoreCase(String name);

    List<Car> findAll(Specification<Car> specification);

    @Query(value = "SELECT * FROM cars c WHERE c.year=:year AND c.name LIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Car> findAllBySearch(@RequestParam(name = "year") Integer year, @RequestParam(name = "name") String name);
}
