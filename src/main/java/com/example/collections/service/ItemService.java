package com.example.collections.service;

import com.example.collections.model.*;
import com.example.collections.repository.ItemRepository;
import com.example.collections.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private TagRepository tagRepo;
    private List<Item> lastAddedItems = new ArrayList<>();

    public Iterable<Item> getItemsByTagAndCollection(String tag, Collection collection) {
        return itemRepo.findAllByTagContainsAndCollection(tag, collection);
    }

    public Iterable<Item> getItemsByTag(Tag tag) {
        return itemRepo.findAllByTagSetContains(tag);
    }

    public Iterable<Item> getItems(Collection collection) {
        return itemRepo.findAllByCollection(collection);
    }

    public void deleteItem(Item item) {
        itemRepo.delete(item);
    }

    public void setTags(Item item, String tag) {
        String[] tags = tag.split(" ");
        String tagResult = "";
        if (item.getTagSet() != null) {
            item.getTagSet().clear();
        } else {
            item.setTagSet(new HashSet<>());
        }
        for (String t : tags) {
            Tag newTag;
            if (t.charAt(0) != '#'){
                t = "#" + t;
            }
            if (tagRepo.findAllByTagName(t).isEmpty()) {
                newTag = new Tag(t);
                tagRepo.save(newTag);
            } else {
                newTag = tagRepo.findByTagName(t);
            }
            item.getTagSet().add(newTag);
            newTag.getItems().add(item);
            tagResult += t + " ";
        }
        item.setTag(tagResult.substring(0, tagResult.length() - 1));
        itemRepo.save(item);
    }

    public void editItem(Item item, Item newItem) {
        item.setName(newItem.getName());
        setTags(item, newItem.getTag());
        item.setCustomBoolean1(newItem.getCustomBoolean1());
        item.setCustomBoolean2(newItem.getCustomBoolean2());
        item.setCustomBoolean3(newItem.getCustomBoolean3());
        item.setCustomInteger1(newItem.getCustomInteger1());
        item.setCustomInteger2(newItem.getCustomInteger2());
        item.setCustomInteger3(newItem.getCustomInteger3());
        item.setCustomString1(newItem.getCustomString1());
        item.setCustomString2(newItem.getCustomString2());
        item.setCustomString3(newItem.getCustomString3());
        item.setCustomMultilineText1(newItem.getCustomMultilineText1());
        item.setCustomMultilineText2(newItem.getCustomMultilineText2());
        item.setCustomMultilineText3(newItem.getCustomMultilineText3());
        itemRepo.save(item);
    }

    public void like(Item item, User user) {
        if (item.getLikes().contains(user)) {
            item.getLikes().remove(user);
        } else {
            item.getLikes().add(user);
        }
        itemRepo.save(item);
    }

    public void addItem(Item item) {
        setTags(item, item.getTag());
    }

    public List<Item> getLastAddedItems() {
        lastAddedItems = itemRepo.getLastFive();
        return this.lastAddedItems;
    }

    public List<Item> getSortByName(Collection collection, Boolean sort) {
        if (sort) {
            return itemRepo.getSortByName(collection.getId());
        } else return itemRepo.getSortByNameDesc(collection.getId());
    }

    public Item getItemById(Long id) {
        return itemRepo.getItemById(id);
    }
}
