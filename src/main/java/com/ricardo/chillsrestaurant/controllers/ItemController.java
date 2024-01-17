package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.dtos.ItemDto;
import com.ricardo.chillsrestaurant.entities.Item;
import com.ricardo.chillsrestaurant.services.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@AllArgsConstructor
public class ItemController
{
    private final ItemService itemService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('MANAGER')")
    public Item addItem(@RequestBody ItemDto itemDto)
    {
        return itemService.saveItem(itemDto);
    }

    @GetMapping("/getAll")
    public List<Item> getAll()
    {
        return itemService.getAllItems();
    }

    @GetMapping("/getAllItemsOnMenu")
    public List<Item> getAllItemsOnMenu()
    {
        return itemService.getAllItemsOnMenu();
    }

    @GetMapping("/get/{id}")
    public Item findById(@PathVariable int id)
    {
        return itemService.findById(id).orElseThrow();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MANAGER')")
    public Item updateItem(@RequestBody ItemDto itemDto)
    {
        return itemService.updateItem(itemDto);
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('MANAGER')")
    public void removeItem(@RequestParam int id)
    {
        itemService.removeItem(id);
    }
}
