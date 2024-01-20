package com.cloudtrack.project;

import com.cloudtrack.project.entity.Board;
import com.cloudtrack.project.entity.Comment;
import com.cloudtrack.project.entity.Post;
import com.cloudtrack.project.repository.BoardRepository;
import com.cloudtrack.project.repository.CommentRepository;
import com.cloudtrack.project.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class DataInit {
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        boardRepository.create(new Board("해외 여행", "다양한 해외 여행 경험들을 공유하세요!!!"));
        boardRepository.create(new Board("국내 여행", "다양한 국내 여행 경험들을 공유하세요!!!"));
        Board worldTravel = boardRepository.findById(1L);
        Board koreaTravel = boardRepository.findById(2L);
        postRepository.save(new Post("(예시) 일본 여행", "(예시) 오사카, 교토, 고베 맛있는거 먹고 재밌게 놀음", worldTravel));
        postRepository.save(new Post("(예시) 제주도 여행", "한라산 등반하고 맛있는거 먹음.", koreaTravel));
        postRepository.save(new Post("서울 여행", "서울 여행 갔다옴", koreaTravel));
        postRepository.save(new Post("부산 여행", "서울 여행 갔다옴", koreaTravel));
        postRepository.save(new Post("강원도 여행", "서울 여행 갔다옴", koreaTravel));
        postRepository.save(new Post("대구 여행", "서울 여행 갔다옴", koreaTravel));
        postRepository.save(new Post("대전 여행", "서울 여행 갔다옴", koreaTravel));
        postRepository.save(new Post("광주 여행", "서울 여행 갔다옴", koreaTravel));
        postRepository.save(new Post("나도 제주도 갔다옴ㅎㅎ", "제주도 또 가고 싶다.", koreaTravel));
        postRepository.save(new Post("제주도!@#","게스트하우스 너무 재밌었음.", koreaTravel));
        postRepository.save(new Post("여수 여행","간장게장 먹으러 갔다옴", koreaTravel));
        Post post1 = postRepository.findById(1L).orElseThrow(()->new RuntimeException());
        Post post2 = postRepository.findById(2L).orElseThrow(()->new RuntimeException());
        commentRepository.save(new Comment("일본 여행 너무 재밌있겠네요 다음엔 삿포로 추천", post1));
        commentRepository.save(new Comment("제주도 너무 재밌겠다. 맛집 추천좀", post2));
        commentRepository.save(new Comment("한라산....", post2));
        commentRepository.save(new Comment("힘들겠다,", post2));
        commentRepository.save(new Comment("한라산 너무 멋있어!!", post2));
        commentRepository.save(new Comment("비행기값얼마임?", post2));
    }
}
