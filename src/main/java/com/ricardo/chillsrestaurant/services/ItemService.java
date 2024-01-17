package com.ricardo.chillsrestaurant.services;

import com.ricardo.chillsrestaurant.dtos.ItemDto;
import com.ricardo.chillsrestaurant.entities.Category;
import com.ricardo.chillsrestaurant.entities.Item;
import com.ricardo.chillsrestaurant.entities.Tag;
import com.ricardo.chillsrestaurant.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService
{
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    public boolean exists(int id)
    {
        return itemRepository.existsById(id);
    }

    public Optional<Item> findById(int id)
    {
        return itemRepository.findById(id);
    }

    public Item saveItem(ItemDto itemDto)
    {
        // Save category if it does not exist
        Category category = categoryService.findByName(itemDto.getCategory()).orElseGet(() ->
                categoryService.saveCategory(itemDto.getCategory()));

        // Save tags if they don't exist
        List<Tag> tags = itemDto.getTags().stream().map(name -> tagService.findByName(name).orElseGet(() ->
                tagService.saveTag(name))).toList();

        Item item = new Item(
                null,
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getPrice(),
                category,
                tags,
                Objects.toString(itemDto.getPicture(), ""),
                false
        );

        return itemRepository.save(item);
    }

    public List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }

    public void removeItem(int id)
    {
        itemRepository.deleteById(id);
    }

    public Item updateItem(ItemDto dto)
    {
        Item item = itemRepository.findById(dto.getId()).orElseThrow();

        if (dto.getName() != null)
            item.setName(dto.getName());

        if (dto.getDescription() != null)
            item.setDescription(dto.getDescription());

        if (dto.getPrice() != null)
            item.setPrice(dto.getPrice());

        if (dto.getCategory() != null)
        {
            Category category = categoryService.findByName(dto.getCategory())
                    .orElseGet(() -> categoryService.saveCategory(dto.getCategory()));

            item.setCategory(category);
        }

        if (dto.getTags() != null)
        {
            List<Tag> tags = dto.getTags().stream().map(name -> tagService.findByName(name)
                    .orElseGet(() -> tagService.saveTag(name))).collect(Collectors.toList());

            item.setTags(tags);
        }

        if (dto.getPicture() != null)
            item.setPicture(dto.getPicture());

        if (dto.getIsOnMenu() != null)
            item.setIsOnMenu(dto.getIsOnMenu());


        return itemRepository.save(item);
    }

    public List<Item> getAllItemsOnMenu()
    {
        return itemRepository.getAllItemsOnMenu();
    }
}
