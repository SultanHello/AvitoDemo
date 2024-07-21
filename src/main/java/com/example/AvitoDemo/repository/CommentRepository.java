package com.example.AvitoDemo.repository;

import com.example.AvitoDemo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
