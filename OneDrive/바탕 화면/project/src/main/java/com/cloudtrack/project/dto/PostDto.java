package com.cloudtrack.project.dto;

import com.cloudtrack.project.Entity.Post;
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
    private String country;
    private String editPassword;

    public Post toEntity(){
        return new Post(title, content, country, editPassword);
    }
}
