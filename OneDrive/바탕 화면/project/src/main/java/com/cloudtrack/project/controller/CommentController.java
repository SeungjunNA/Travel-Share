package com.cloudtrack.project.controller;

import com.cloudtrack.project.dto.CommentDto;
import com.cloudtrack.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/comment")
    public String createComment(@ModelAttribute("commentDto") CommentDto commentDto, @RequestParam("postId") long postId){
        commentService.createComment(commentDto, postId);
        return "redirect:/travel/detail-post/"+postId;
    }
}
