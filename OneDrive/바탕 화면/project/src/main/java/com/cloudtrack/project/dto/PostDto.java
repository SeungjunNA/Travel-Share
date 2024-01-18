package com.cloudtrack.project.dto;

import com.cloudtrack.project.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String content;
    private long boardId;

    public PostDto(String title, String content){
        this.title = title;
        this.content = content;
    }
    public Post toEntity(){
        return new Post(title, content);
    }
}
