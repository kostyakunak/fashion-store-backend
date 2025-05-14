package com.kounak.backend.service;

import com.kounak.backend.model.Category;
import com.kounak.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(Category category) {
        validateCategory(category);
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Категория с таким названием уже существует");
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Long id, Category category) {
        validateCategory(category);
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            category.setId(id);
            try {
                return categoryRepository.save(category);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("Категория с таким названием уже существует");
            }
        }
        throw new RuntimeException("Категория не найдена");
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Категория не найдена");
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Невозможно удалить категорию, так как она используется в товарах");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new RuntimeException("Категория не может быть пустой");
        }
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new RuntimeException("Название категории не может быть пустым");
        }
        if (category.getName().trim().length() < 2) {
            throw new RuntimeException("Название категории должно содержать минимум 2 символа");
        }
        // Очищаем название от пробелов в начале и конце
        category.setName(category.getName().trim());
    }
}