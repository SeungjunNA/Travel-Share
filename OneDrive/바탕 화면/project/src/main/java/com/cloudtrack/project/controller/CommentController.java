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
    public String create(@ModelAttribute(name = "commentDto") CommentDto commentDto, @RequestParam(name = "postId") long postId){
        try {
            if(commentDto.getContent().trim().equals("")){
                throw new IllegalArgumentException("문자를 포함하여 입력해주세요.(공백만 입력 불가)");
            }else if(commentDto.getContent().trim().length()>100){
                throw new IllegalArgumentException("100자 이내로 작성해주세요.");
            }
            commentService.createComment(commentDto, postId);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return "redirect:/travel/detail-post/"+postId;
    }
    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable(name = "commentId") long commentId){
        int page = commentService.afterDeletePage(commentId);
        long postId = commentService.delete(commentId);
        if (postId == 0){
            return "redirect:/board/home";
        }
        return "redirect:/travel/detail-post/"+postId+"?page="+page;
    }
}
