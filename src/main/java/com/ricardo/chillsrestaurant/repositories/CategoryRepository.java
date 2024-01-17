package com.ricardo.chillsrestaurant.repositories;

import com.ricardo.chillsrestaurant.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Integer>
{
    Optional<Category> findCategoryByName(String name);
}
