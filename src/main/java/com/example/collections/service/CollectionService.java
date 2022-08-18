package com.example.collections.service;

import com.example.collections.model.Collection;
import com.example.collections.model.Item;
import com.example.collections.model.User;
import com.example.collections.repository.CollectionsRepository;
import com.example.collections.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    private CollectionsRepository collectionsRepo;
    @Autowired
    private ItemRepository itemRepo;
    private List<Collection> largestCol = new ArrayList<>();

    public Iterable<Item> getItems(Collection col) {
        return itemRepo.findAllByCollection(col);
    }

    public void editCollection(Collection collection, Collection newCollection) {
        collection.setName(newCollection.getName());
        collection.setDescription(newCollection.getDescription());
        collectionsRepo.save(collection);
    }

    public Iterable<Collection> getCollections(User user) {
        return collectionsRepo.findAllByOwner(user);
    }

    public void deleteCollection(Collection collection) {
        collectionsRepo.delete(collection);
    }

    public Iterable<Collection> getAllCollections() {
        return collectionsRepo.findAll();
    }

    public Collection getMaxSizeCollection() {
        Iterable<Collection> collections = getAllCollections();
        Collection maxSize = collections.iterator().next();
        for (Collection col : collections) {
            if (col.getItems().size() > maxSize.getItems().size())
                maxSize = col;
        }
        return maxSize;
    }
    public List<Collection> getLargestCol() {
        largestCol = collectionsRepo.getLargestCollections();
        return this.largestCol;
    }

    public void saveCollection(Collection collection) {
        collectionsRepo.save(collection);
    }
}
