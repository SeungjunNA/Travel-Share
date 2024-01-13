package com.cloudtrack.project.controller;

import com.cloudtrack.project.Entity.Comment;
import com.cloudtrack.project.Entity.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.service.CommentService;
import com.cloudtrack.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/travel")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    CommentService commentService;

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("msg", "hello world");
        return "home";
    }

   @GetMapping("/world")
    public String getWorldTravelPost(@PageableDefault(size = 2, page = 0) Pageable pageable, Model model){
        Page<Post> worldTravelPosts = postService.getWorldTravelPost(pageable);

        int page = pageable.getPageNumber();
        int totalPage = worldTravelPosts.getTotalPages();

        int preBtn = page==totalPage-1 ? Math.max(0,page-2) : Math.max(0, page-1);
        int nextBtn = page==0 ? Math.min(page+2, totalPage-1) : Math.min(page+1, totalPage-1);

        model.addAttribute("posts", worldTravelPosts.getContent());
        model.addAttribute("preBtn", preBtn);
        model.addAttribute("nextBtn", nextBtn);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPage);
        return "world-travel";
    }

    @GetMapping("/korea")
    public String getKoreaTravelPost(@PageableDefault(page = 0, size = 10) Pageable pageable, Model model) {
        Page<Post> koreaTravelPosts = postService.getKoreaTravelPost(pageable);

        int page = pageable.getPageNumber();
        int totalPage = koreaTravelPosts.getTotalPages();

        int preBtn = page==totalPage-1 ? Math.max(0,page-2) : Math.max(0, page-1);
        int nextBtn = page==0 ? Math.min(page+2, totalPage-1) : Math.min(page+1, totalPage-1);

        model.addAttribute("posts", koreaTravelPosts.getContent());
        model.addAttribute("preBtn", preBtn);
        model.addAttribute("nextBtn", nextBtn);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPage);
        return "korea-travel";
    }

    @GetMapping("/detail-post/{postId}")
    public String getPostDetailPage(@PathVariable("postId") long postId, Model model,
                                    @PageableDefault(size = 2, page = 0) Pageable pageable){
        Optional<Post> optionalPost = postService.findByIdPost(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            Page<Comment> comments = commentService.getComments(postId, pageable);
            int page = pageable.getPageNumber();
            int totalPage = comments.getTotalPages();

            int preBtn = page==totalPage-1 ? Math.max(0,page-2) : Math.max(0, page-1);
            int nextBtn = page==0 ? Math.min(page+2, totalPage-1) : Math.min(page+1, totalPage-1);

            model.addAttribute("comments", comments.getContent());
            model.addAttribute("preBtn", preBtn);
            model.addAttribute("nextBtn", nextBtn);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPage);
        }
        return "detail-post";
    }

    @GetMapping("/create-form")
    public String getCreatePostPage(Model model){
        model.addAttribute("postDto", new PostDto());
        return "create-post";
    }

    @GetMapping("/post-edit-form/{postId}")
    public String editPost(@PathVariable("postId") long postId, Model model){
        Optional<Post> optionalPost = postService.findByIdPost(postId);

        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post-edit-form";
        }
        return "home";
    }

    @PostMapping("/create-post")
    public String createPost(@ModelAttribute("postDto") PostDto postDto){
        Post savedPost = postService.createPost(postDto);
        if(savedPost == null){
            return ":/redirect";
        }
        return "home";
    }

    @PostMapping("/post-update")
    public String updatePost(@ModelAttribute("postDto") PostDto postDto){
        postService.updatePost(postDto);
        return "home";
    }

    @PostMapping("/search")
    public String searchPost(@RequestParam("word") String word, Model model,
                             @PageableDefault(size = 2, page = 0) Pageable pageable){
        Page<Post> searchPosts = postService.getSearchPost(word, pageable);
        model.addAttribute("posts", searchPosts.getContent());
        return "world-travel";
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable("postId") long postId){
        postService.deletePost(postId);
        return "home";
    }
}
