package com.example.collections.repository;

import com.example.collections.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User ear set ear.photos = :photos where ear.username = :username")
    int setPhoto(@Param("photos") String photos, @Param("username") String username);
}
