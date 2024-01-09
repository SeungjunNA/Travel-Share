package com.cloudtrack.project.dto;

import com.cloudtrack.project.domain.Post;
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
//    public PostDto(String title, String content, String country, String editPassword){
//        this.title = title;
//        this.content = content;
//        this.country = country;
//        this.editPassword = editPassword;
//    }
    public Post toEntity(){
        return new Post(title, content, country, editPassword);
    }
}
