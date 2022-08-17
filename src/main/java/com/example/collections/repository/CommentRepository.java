package com.example.collections.repository;

import com.example.collections.model.Comment;
import com.example.collections.model.Item;
import com.example.collections.model.User;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Iterable<Comment> findAllByAuthor(User author);
    Iterable<Comment> findAllByItem(Item item);
}
