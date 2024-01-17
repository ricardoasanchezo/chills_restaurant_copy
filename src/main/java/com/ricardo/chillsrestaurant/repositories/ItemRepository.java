package com.ricardo.chillsrestaurant.repositories;

import com.ricardo.chillsrestaurant.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Integer>
{
    @Query("SELECT i FROM Item i WHERE i.isOnMenu = true")
    List<Item> getAllItemsOnMenu();
}
