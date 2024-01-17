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
    private String editPassword;
    private long boardId;

    public PostDto(String title, String content, String editPassword){
        this.title = title;
        this.content = content;
        this.editPassword = editPassword;
    }
    public Post toEntity(){
        return new Post(title, content, editPassword);
    }
}
