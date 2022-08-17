package com.example.collections.repository;

import com.example.collections.model.Item;
import com.example.collections.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findAllByTagName(String tagName);
    Tag findByTagName(String tagName);
    List<Tag> findAllByItemsContains(Item item);
}
