package com.cloudtrack.project.repository;

import com.cloudtrack.project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByBoardIdOrderByIdDesc(Pageable pageable, Long boardId);

    @Query(value = "SELECT * FROM Post p WHERE p.board_id = :boardId AND (p.title LIKE %:word% OR p.content LIKE %:word%) Order By p.id DESC", nativeQuery = true)
    Page<Post> findByBoardIdAndTitleOrContent(@Param("boardId") long boardId, @Param("word") String word, Pageable pageable);


}
