package com.example.collections.repository;

import com.example.collections.model.Collection;
import com.example.collections.model.Item;
import com.example.collections.model.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findAllByCollection(Collection col);
    Iterable<Item> findByTag(String tag);
    Iterable<Item> findAllByTagSetContains(Tag tag);
    Iterable<Item> findAllByTagContainsAndCollection(String tag, Collection collection);
    @Query(value="SELECT * FROM Item ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Item> getLastFive();
    @Query(value = "SELECT * FROM Item where item.collection_id = ? ORDER BY item.name", nativeQuery = true)
    List<Item> getSortByName(Long collection_id);
    @Query(value = "SELECT * FROM Item where item.collection_id = ? ORDER BY item.name desc", nativeQuery = true)
    List<Item> getSortByNameDesc(Long collection_id);
    Item getItemById(Long id);

    @Modifying
    @Transactional
    @Query("update Item ear set ear.photos = :photos where ear.id = :id")
    int setPhoto(@Param("photos") String photos, @Param("id") long id);
}
