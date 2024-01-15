package com.cloudtrack.project.service;

import com.cloudtrack.project.Entity.Comment;
import com.cloudtrack.project.Entity.Post;
import com.cloudtrack.project.dto.CommentDto;
import com.cloudtrack.project.repository.CommentRepository;
import com.cloudtrack.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    public void createComment(CommentDto commentDto, long postId){
        Comment comment = commentDto.toEntity();
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            comment.setPost(post);
            commentRepository.save(comment);
        }
    }

    public Page<Comment> getComments(long postId, Pageable pageable){
        return commentRepository.findByPostIdOrderByIdDesc(postId, pageable);
    }
}
