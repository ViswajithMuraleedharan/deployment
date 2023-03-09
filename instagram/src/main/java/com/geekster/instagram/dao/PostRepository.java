package com.geekster.instagram.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.geekster.instagram.model.Post;
public interface PostRepository extends JpaRepository<Post,Integer> {
}
