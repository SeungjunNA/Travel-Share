package com.cloudtrack.project.service;

import com.cloudtrack.project.dto.CommentDto;
import com.cloudtrack.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void createComment(CommentDto commentDto){
        commentRepository.save(commentDto.toEntity());
    }
}
