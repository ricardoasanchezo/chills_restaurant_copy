package com.ricardo.chillsrestaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemDto
{
    private Integer id;
    private String name;
    private String description;
    private Integer price; // Stored as an integer amount of cents
    private String category;
    private List<String> tags;
    private String picture;
    private Boolean isOnMenu;
}
