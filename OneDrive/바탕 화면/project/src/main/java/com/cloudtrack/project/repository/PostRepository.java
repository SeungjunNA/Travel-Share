package com.cloudtrack.project.repository;

import com.cloudtrack.project.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCountryContainsOrCountryContains(String country1, String country2, Pageable pageable);

    Page<Post> findAllByOrderByIdDesc(Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.title LIKE %:word% OR p.content LIKE %:word% OR p.country LIKE %:word%")
    Page<Post> findByTitleOrContent(@Param("word") String word, Pageable pageable);

}
