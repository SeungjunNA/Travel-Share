package com.cloudtrack.project;

import com.cloudtrack.project.repository.BoardRepository;
import com.cloudtrack.project.repository.CommentRepository;
import com.cloudtrack.project.repository.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	@Bean
	public DataInit stuDataInit(BoardRepository boardRepository, PostRepository postRepository, CommentRepository commentRepository){
		return new DataInit(boardRepository, postRepository, commentRepository);
	}

}
