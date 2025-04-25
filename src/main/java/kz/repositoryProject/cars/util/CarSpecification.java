package kz.repositoryProject.cars.util;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.entity.Category;
import kz.repositoryProject.cars.entity.Country;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CarSpecification {
    public Specification<Car> getCarSpecification(Integer year, String name, Integer maxPrice, Long countryId, Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (year != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"), year));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if (countryId != null) {
                Join<Car, Country> countryJoin = root.join("country");
                predicates.add(criteriaBuilder.equal(countryJoin.get("id"), countryId));
            }

            if (categoryId != null) {
                Join<Car, Category> categoryJoin = root.join("categories");
                predicates.add(criteriaBuilder.equal(categoryJoin.get("id"), categoryId));
            }

            Predicate commonPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));

            return commonPredicate;
        };
    }
}
