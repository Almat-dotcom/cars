package kz.repositoryProject.cars.service;

import kz.repositoryProject.cars.entity.Car;
import kz.repositoryProject.cars.entity.Category;
import kz.repositoryProject.cars.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void addCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .build();
        categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getNotAssignedCategories(Car car){
        return categoryRepository.findAllByCarsNotContaining(car);
    }
}
