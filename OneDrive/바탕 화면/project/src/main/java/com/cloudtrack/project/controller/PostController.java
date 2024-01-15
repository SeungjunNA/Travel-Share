package com.cloudtrack.project.controller;

import com.cloudtrack.project.Entity.Board;
import com.cloudtrack.project.Entity.Comment;
import com.cloudtrack.project.Entity.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.service.BoardService;
import com.cloudtrack.project.service.CommentService;
import com.cloudtrack.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Controller
@RequestMapping("/travel")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardService boardService;

    @GetMapping("/{boardTitle}")
    public String getBoard(@PageableDefault(size = 2, page = 0)Pageable pageable, Model model,
                           @PathVariable String boardTitle){
        return handleTravelPost(pageable, model, "travel-board", pageableObj -> postService.getPostsByBoardTitle(pageableObj, boardTitle));
    }

    @GetMapping("/detail-post/{postId}")
    public String getPostDetailPage(@PathVariable("postId") long postId, Model model,
                                    @PageableDefault(size = 2, page = 0) Pageable pageable){
        Optional<Post> optionalPost = postService.findByIdPost(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post",post);
            return handleTravelPost(pageable, model, "detail-post", pageableObj -> commentService.getComments(postId, pageableObj));
        }
//        else{
//            에러 페이지 생성 예정
//        }
        return "home";
    }

    @GetMapping("/create-form")
    public String getCreatePostPage(Model model){
        List<Board> boards = boardService.findAll();
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("boards", boards);
        return "create-post";
    }

    @GetMapping("/post-edit-form/{postId}")
    public String editPost(@PathVariable("postId") long postId, Model model){
        Optional<Post> optionalPost = postService.findByIdPost(postId);

        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "post-edit-form";
        }
//        else{
//            에러 페이지 작성예정
//        }
        return "home";
    }

    @PostMapping("/create-post")
    public String createPost(@RequestParam String title, @RequestParam String content,
            @RequestParam String editPassword, @RequestParam("boardTitle") String boardTitle){
        PostDto postDto = new PostDto(title, content, editPassword);
        Post savedPost = postService.createPost(postDto, boardTitle);

        if(savedPost == null){
            return "redirect:/board/home"; // 게시글 생성 오류시 홈화면으로 리다이렉션
        }
        return "redirect:/travel/" + boardTitle;
    }


    @PostMapping("/post-update")
    public String updatePost(@ModelAttribute("postDto") PostDto postDto){
        postService.updatePost(postDto);
        return "home";
    }

    @PostMapping("/search")
    public String searchPost(@RequestParam("word") String word, Model model,
                             @PageableDefault(size = 2, page = 0) Pageable pageable){
        Page<Post> searchPosts = postService.getSearchPost(word, pageable);
        model.addAttribute("posts", searchPosts.getContent());
        
        // 페이지네이션 적용
        return "world-travel";
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable("postId") long postId){
        postService.deletePost(postId);
        return "home";
    }

    private <T> String handleTravelPost(Pageable pageable, Model model,
                                        String viewName, Function<Pageable, Page<T>> getPageFunction){
        Page<T> page = getPageFunction.apply(pageable);

        int currentPage = pageable.getPageNumber();
        int totalPage = page.getTotalPages();

        int preBtn = currentPage==totalPage-1 ? Math.max(0,currentPage-2) : Math.max(0, currentPage-1);
        int nextBtn = currentPage==0 ? Math.min(currentPage+2, totalPage-1) : Math.min(currentPage+1, totalPage-1);

        model.addAttribute("content", page.getContent());
        model.addAttribute("preBtn", preBtn);
        model.addAttribute("nextBtn", nextBtn);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPage);

        return viewName;
    }
}
