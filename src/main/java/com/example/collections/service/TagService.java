package com.example.collections.service;

import com.example.collections.model.Tag;
import com.example.collections.repository.ItemRepository;
import com.example.collections.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    ItemRepository itemRepo;
    @Autowired
    TagRepository tagRepo;

    public Iterable<Tag> getAllTags() {
        return tagRepo.findAll();
    }
}
