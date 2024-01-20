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
    @Column(length = 30)
    private String title;
    @Column(length = 1000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    public Post (String title, String content){
        this.title = title;
        this.content = content;
    }
    public Post (String title, String content, Board board){
        this.title = title;
        this.content = content;
        this.board = board;
    }
}
