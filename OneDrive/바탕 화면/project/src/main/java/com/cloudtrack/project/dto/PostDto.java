package com.cloudtrack.project.dto;

import com.cloudtrack.project.domain.Post;

public class PostDto {
    private long id;
    private String title;
    private String content;
    private String country;
    public Post toEntity(){
        return new Post(title, content, country);
    }
}
