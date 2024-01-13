package com.cloudtrack.project.repository;

import com.cloudtrack.project.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCountryContainsOrCountryContains(String country1, String country2, Pageable pageable);

    Page<Post> findAllByOrderByIdDesc(Pageable pageable);
}
