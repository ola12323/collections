package com.example.collections.service;

import com.example.collections.model.Comment;
import com.example.collections.model.Item;
import com.example.collections.model.User;
import com.example.collections.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    public Iterable<Comment> getCommentsByItem(Item item) {
        return commentRepo.findAllByItem(item);
    }

    public void addComment(String comment, User author, Item item) {
        Comment comm = new Comment(comment, author, item);
        commentRepo.save(comm);
    }
}
