package com.springbootapi.repository;

import com.springbootapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PostRepository extends JpaRepository<Post, Long> {
    //
}
