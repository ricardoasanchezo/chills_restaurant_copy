package com.ricardo.chillsrestaurant.services;


import com.ricardo.chillsrestaurant.entities.Tag;
import com.ricardo.chillsrestaurant.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService
{
    private final TagRepository tagRepository;

    public List<Tag> getAllTags()
    {
        return tagRepository.findAll();
    }

    public Optional<Tag> findByName(String name)
    {
        return tagRepository.findByName(name);
    }

    public Tag saveTag(String name)
    {
        return tagRepository.save(new Tag(null, name));
    }

    public Tag saveTag(Tag tag)
    {
        return tagRepository.save(tag);
    }
}

