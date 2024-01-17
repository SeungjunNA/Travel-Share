package com.cloudtrack.project.repository;

import com.cloudtrack.project.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findByPostIdOrderByIdDesc(long postId, Pageable pageable);
}
