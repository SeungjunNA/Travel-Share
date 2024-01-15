package com.cloudtrack.project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;
    @Column(name = "edit_password")
    private String editPassword;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;
    public Post (String title, String content, String editPassword){
        this.title = title;
        this.content = content;
        this.editPassword = editPassword;
    }
}
