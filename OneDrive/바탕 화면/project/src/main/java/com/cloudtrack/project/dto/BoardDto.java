package com.cloudtrack.project.dto;

import com.cloudtrack.project.Entity.Board;
import com.cloudtrack.project.Entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardDto {
    private long id;
    private String title;
    private String description;
    private List<Post> posts;
    public Board toEntity(){
        return new Board(title, description);
    }
}
