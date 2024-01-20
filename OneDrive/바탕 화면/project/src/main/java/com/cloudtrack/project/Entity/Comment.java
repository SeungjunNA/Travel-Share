package com.cloudtrack.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(length = 100)
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    public Comment(String content) {
        this.content = content;
    }
    public Comment(String content, Post post) {
        this.content = content;
        this.post = post;
    }
}

