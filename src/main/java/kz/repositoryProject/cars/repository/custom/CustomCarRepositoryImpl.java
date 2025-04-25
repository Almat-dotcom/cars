package kz.repositoryProject.cars.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kz.repositoryProject.cars.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCarRepositoryImpl implements CustomCarRepository {

    private final EntityManager entityManager;

    @Override
    public List<Car> findAllByCriteria(Integer year, String name, Integer maxPrice, Long countryId, Long categoryId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);
        List<Predicate> predicates = new ArrayList<>();

        if (year != null) {
            Predicate predicateYear = criteriaBuilder.greaterThanOrEqualTo(root.get("year"), year);
            predicates.add(predicateYear);
        }

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }

        if (maxPrice != null) {
            Predicate predicateMaxPrice = criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            predicates.add(predicateMaxPrice);
        }

        if (countryId != null) {
            Predicate predicateCountry = criteriaBuilder.equal(root.get("country").get("id"), countryId);
            predicates.add(predicateCountry);
        }

        if (categoryId != null) {
            Predicate predicateCategory = criteriaBuilder.equal(root.get("categories").get("id"), categoryId);
            predicates.add(predicateCategory);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Car> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Car> cars = typedQuery.getResultList();
        return cars;
    }
}
