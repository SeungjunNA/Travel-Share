package com.cloudtrack.project.service;

import com.cloudtrack.project.Entity.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(PostDto postDto){
        Post post = postDto.toEntity();
        post.setCreatedDateTime(LocalDateTime.now());
        return postRepository.save(post);
    }
    public void updatePost(PostDto postDto){
        Optional<Post> optionalPost = postRepository.findById(postDto.getId());
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();

            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            postRepository.save(post);
        }
    }

    public Page<Post> getKoreaTravelPost(Pageable pageable) {
        return postRepository.findByCountryContainsOrCountryContains
                ("korea", "한국", pageable);
    }
    public Page<Post> getWorldTravelPost(Pageable pageable) {
        return postRepository.findAllByOrderByIdDesc(pageable);
    }

    public Optional<Post> findByIdPost(long postId){
        return postRepository.findById(postId);
    }

    public void deletePost(long postId){
        postRepository.deleteById(postId);
    }

    public Page<Post> getSearchPost(String word, Pageable pageable){
        return postRepository.findByTitleOrContent(word, pageable);
    }

    public Page<Post> getAbroadTravelPost(Pageable pageable){
        List<String> country = Arrays.asList("한국", "korea");
        return postRepository.findAbroadPost(country, pageable);
    }
}
