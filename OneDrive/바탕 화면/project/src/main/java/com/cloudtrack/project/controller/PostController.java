package com.cloudtrack.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/travel")
public class PostController {
    @GetMapping
    public String getMainPage(Model model){
        model.addAttribute("msg","hello world");
        return "home";
    }

    @GetMapping("/createPost")
    public String getCreatePostPage(){
        return "create-post";
    }
}
