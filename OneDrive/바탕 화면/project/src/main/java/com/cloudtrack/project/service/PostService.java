package com.cloudtrack.project.service;

import com.cloudtrack.project.domain.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(PostDto postDto){
        Post post = postDto.toEntity();
        post.setCreatedDateTime(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Page<Post> getPostsPage(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return postRepository.findAll(pageable);
    }
}
