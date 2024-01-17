package com.ricardo.chillsrestaurant.services;

import com.ricardo.chillsrestaurant.entities.Category;
import com.ricardo.chillsrestaurant.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public boolean exists(int id)
    {
        return categoryRepository.existsById(id);
    }

    public Optional<Category> findById(int id)
    {
        return categoryRepository.findById(id);
    }

    public Optional<Category> findByName(String name)
    {
        return categoryRepository.findCategoryByName(name);
    }

    public Category saveCategory(String name)
    {
        return categoryRepository.save(new Category(null, name));
    }

    public Category saveCategory(Category category)
    {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    public void removeCategory(int id)
    {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(Category category)
    {
        categoryRepository.save(category);
    }
}
