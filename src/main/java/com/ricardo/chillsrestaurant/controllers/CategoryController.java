package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.entities.Category;
import com.ricardo.chillsrestaurant.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<String> addCategory(@RequestBody Category category)
    {
        try
        {
            categoryService.saveCategory(category);
            return ResponseEntity.ok("Category saved successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving category");
        }
    }

    @GetMapping("/getAllCategories")
    public List<Category> getAllCategories()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/{id}")
    public Category findById(@PathVariable int id)
    {
        return categoryService.findById(id).orElseThrow();
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<String> updateCategory(@RequestBody Category category)
    {
        try
        {
            if (categoryService.exists(category.getId()))
            {
                categoryService.updateCategory(category);
                return ResponseEntity.ok("Category updated successfully");
            }
            else
            {
                return ResponseEntity.ok("Category can't be updated because it doesn't exist");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating category");
        }
    }
}
