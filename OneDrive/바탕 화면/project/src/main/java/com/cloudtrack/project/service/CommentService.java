package com.cloudtrack.project.service;

import com.cloudtrack.project.entity.Comment;
import com.cloudtrack.project.entity.Post;
import com.cloudtrack.project.dto.CommentDto;
import com.cloudtrack.project.repository.CommentRepository;
import com.cloudtrack.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public long delete(long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        try {
            if(!optionalComment.isPresent()){
                throw new RuntimeException("댓글 찾기 실패");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return 0;
        }
        Comment comment = optionalComment.get();
        long postId = comment.getPost().getId();
        commentRepository.deleteById(commentId);
        return postId;
    }
    public List<Comment> findByPostId(long postId){
        return commentRepository.findByPostId(postId);
    }
    public int afterDeletePage(long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(!optionalComment.isPresent()){
            throw new RuntimeException("댓글 찾기 실패");
        }
        Comment comment = optionalComment.get();
        List<Comment> comments = commentRepository.findByPostId(comment.getPost().getId());
        int count = 0;
        for(Comment c : comments){
            if(c.getId() > commentId) count++;
        }
        int page = comments.size()%5==1 ? count/5-1 : count/5;
        return page;
    }
}
