package com.cloudtrack.project.service;

import com.cloudtrack.project.Entity.Board;
import com.cloudtrack.project.Entity.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.repository.BoardRepository;
import com.cloudtrack.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardRepository boardRepository;

    public long createPost(PostDto postDto, String boardTitle){
        Post post = postDto.toEntity();
        post.setCreatedDateTime(LocalDateTime.now());
        Board board = boardRepository.findByBoardTitle(boardTitle);
        post.setBoard(board);
        postRepository.save(post);
        return board.getId();
    }
    public long updatePost(PostDto postDto){
        Optional<Post> optionalPost = postRepository.findById(postDto.getId());
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();

            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            postRepository.save(post);

            return post.getId();
        }
        return 0;
    }

    public Page<Post> getPostsByBoardTitle(Pageable pageable, long boardId){
        return postRepository.findByBoardIdOrderByIdDesc(pageable, boardId);
    }

    public Optional<Post> findByIdPost(long postId){
        return postRepository.findById(postId);
    }

    public long deletePost(long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);

        if(!optionalPost.isPresent()){
            throw new RuntimeException("post 게시물 찾기 실패  id : " + postId);
        }
        Post post = optionalPost.get();
        long boardId = post.getBoard().getId();
        postRepository.deleteById(postId);
        return boardId;
    }

    public Page<Post> getSearchPost(String word, Pageable pageable){
        return postRepository.findByTitleOrContent(word, pageable);
    }
}
