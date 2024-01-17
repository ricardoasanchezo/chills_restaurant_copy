package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.entities.Tag;
import com.ricardo.chillsrestaurant.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@AllArgsConstructor
public class TagController
{
    private final TagService tagService;
    @PostMapping("/add")
    public void addTag(@RequestBody Tag newTag)
    {
        tagService.saveTag(newTag);
    }

    @GetMapping("/getAllTags")
    public List<Tag> getAllTags()
    {
        return tagService.getAllTags();
    }
}


