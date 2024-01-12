package com.cloudtrack.project.dto;

import com.cloudtrack.project.Entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private long commentId;
    private String content;
    private long postId;

    public Comment toEntity(){
        return new Comment(content);
    }
}
