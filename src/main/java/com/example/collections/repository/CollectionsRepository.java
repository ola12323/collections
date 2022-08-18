package com.example.collections.repository;

import com.example.collections.model.Collection;
import com.example.collections.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollectionsRepository extends CrudRepository<Collection, Long> {
    Collection findCollectionByName(String name);
    Iterable<Collection> findAllByOwner(User user);
    @Modifying
    @Transactional
    @Query("update Collection ear set ear.photos = :photos where ear.id = :id")
    int setPhoto(@Param("photos") String photos, @Param("id") long id);

    @Query(nativeQuery = true,value = "select  c.name,c.description,c.theme,c.id from collection c join item i on c.id = i.collection_id group by  c.id order by count(i.id) desc limit 5")
    List<Collection> getLargestCollections();

}
