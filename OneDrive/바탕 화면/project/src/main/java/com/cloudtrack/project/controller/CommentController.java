package com.cloudtrack.project.controller;

import com.cloudtrack.project.dto.CommentDto;
import com.cloudtrack.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/create")
    public String create(@ModelAttribute("commentDto") CommentDto commentDto, @RequestParam("postId") long postId){
        commentService.createComment(commentDto, postId);
        return "redirect:/travel/detail-post/"+postId;
    }
    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable long commentId){
        long postId = commentService.delete(commentId);
        return "redirect:/travel/detail-post/"+postId;
    }
}
