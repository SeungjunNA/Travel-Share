package com.cloudtrack.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 30)
    private String title;
    @Column(length = 100)
    private String description;
    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();
    public Board(String title, String description){
        this.title = title;
        this.description = description;
    }
}
