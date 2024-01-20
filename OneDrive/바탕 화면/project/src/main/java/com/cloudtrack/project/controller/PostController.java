package com.cloudtrack.project.controller;

import com.cloudtrack.project.entity.Board;
import com.cloudtrack.project.entity.Comment;
import com.cloudtrack.project.entity.Post;
import com.cloudtrack.project.dto.PostDto;
import com.cloudtrack.project.service.BoardService;
import com.cloudtrack.project.service.CommentService;
import com.cloudtrack.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{boardId}")
    public String getBoard(@PageableDefault(size = 10, page = 0)Pageable pageable, Model model,
                           @PathVariable(name = "boardId") long boardId){
        Board board = boardService.findById(boardId);
        try {
            if(board == null){
                throw new RuntimeException("조회하려는 board 게시판 찾기 실패 ");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return "redirect:/board/home";
        }
        model.addAttribute("board", board);
        return handleTravelPost(pageable, model, "travel-board", pageableObj -> postService.getPostsByBoardTitle(pageableObj, boardId));
    }

    @GetMapping("/detail-post/{postId}")
    public String getPostDetailPage(@PathVariable(name = "postId") long postId, Model model,
                                    @PageableDefault(size = 5, page = 0) Pageable pageable){
        Optional<Post> optionalPost = postService.findByIdPost(postId);
        try {
            if(!optionalPost.isPresent()){
                throw new RuntimeException("조회하려는 post 게시글 찾기 실패");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return "redirect:/board/home";
        }
        Post post = optionalPost.get();
        List<Comment> comments = commentService.findByPostId(postId);
        model.addAttribute("post",post);
        model.addAttribute("comments", comments);
        return handleTravelPost(pageable, model, "detail-post", pageableObj -> commentService.getComments(postId, pageableObj));
    }

    @GetMapping("/create-form")
    public String getCreatePostPage(Model model){
        List<Board> boards = boardService.findAll();
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("boards", boards);
        return "create-post";
    }

    @GetMapping("/post-edit-form/{postId}")
    public String editPost(@PathVariable(name = "postId") long postId, Model model){
        Optional<Post> optionalPost = postService.findByIdPost(postId);
        try {
            if(!optionalPost.isPresent()){
                throw new RuntimeException("수정하려는 post 게시글 찾기 실패");
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return "redirect:board/home";
        }
        Post post = optionalPost.get();
        model.addAttribute("post", post);
        return "post-edit-form";
    }

    @PostMapping("/create-post")
    public String createPost(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content,
                             @RequestParam(name = "boardTitle") String boardTitle){
        PostDto postDto = new PostDto(title, content);
        Long boardId = postService.createPost(postDto, boardTitle);
        return "redirect:/travel/" + boardId;
    }


    @PostMapping("/post-update")
    public String updatePost(@ModelAttribute(name = "postDto") PostDto postDto){
        long boardId = postService.updatePost(postDto);
        return "redirect:/travel/detail-post/"+postDto.getId();
    }

    @GetMapping("/search/{boardId}")
    public String searchPost(@RequestParam(name = "word") String word, @PathVariable(name = "boardId") long boardId,
                             Model model, @PageableDefault(size = 10, page = 0) Pageable pageable){
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        model.addAttribute("word", word);
        return handleTravelPost(pageable, model, "travel-board", pageableObj -> postService.getSearchPost(boardId,  word, pageableObj));
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable(name = "postId") long postId){
        long boardId = postService.deletePost(postId);
        return "redirect:/travel/" + boardId;
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
