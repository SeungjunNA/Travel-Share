package com.cloudtrack.project.controller;

import com.cloudtrack.project.domain.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/travel")
public class PostController {
    @Autowired
    private PostService postService;
    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("msg", "hello world");
        return "home";
    }
    @GetMapping("/world")
//    public String getWorldTravelPost(Model model){
//        List<Post> posts = postService.getAllPosts();
//        model.addAttribute("posts", posts);
    public String getWorldTravelPost(@RequestParam(defaultValue = "0") int page, Model model){
        int pageSize = 2;
        Page<Post> postPage = postService.getPostsPage(page, pageSize);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "world-travel";
    }

    @GetMapping("/createPost")
    public String getCreatePostPage(Model model){
        model.addAttribute("postDto", new PostDto());
        return "create-post";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute PostDto postDto){
        Post savedPost =postService.createPost(postDto);
        if(savedPost == null){
            return ":/redirect";
        }
        return "home";
    }


}
