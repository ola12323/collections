package com.example.collections.repository;

import com.example.collections.model.Collection;
import com.example.collections.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CollectionsRepository extends CrudRepository<Collection, Long> {
    Collection findCollectionByName(String name);
    Iterable<Collection> findAllByOwner(User user);
    @Modifying
    @Transactional
    @Query("update Collection ear set ear.photos = :photos where ear.id = :id")
    int setPhoto(@Param("photos") String photos, @Param("id") long id);
}
