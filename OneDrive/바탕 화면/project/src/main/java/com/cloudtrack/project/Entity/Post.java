package com.cloudtrack.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "edit_password")
    private String editPassword;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    public Post (String title, String content, String editPassword){
        this.title = title;
        this.content = content;
        this.editPassword = editPassword;
    }
}
