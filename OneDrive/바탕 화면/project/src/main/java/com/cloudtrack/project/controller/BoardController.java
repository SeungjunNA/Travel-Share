package com.cloudtrack.project.controller;

import com.cloudtrack.project.Entity.Board;
import com.cloudtrack.project.dto.BoardDto;
import com.cloudtrack.project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/home")
    public String homeBoard(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "home";
    }
    @GetMapping("/create-form")
    public String createBoardForm(Model model){
        model.addAttribute("boardDto", new BoardDto());
        return "create-board";
    }
//    @GetMapping("/travel/{boardTitle}")
//    public String getBoard(@PageableDefault(size = 3, page = 0)Pageable pageable, Model model,
//                           @PathVariable String boardTitle){
//
//    }
    @PostMapping("/create-board")
    public String createBoard(@ModelAttribute("boardDto") BoardDto boardDto){
        Board board = boardService.create(boardDto);
        if(board == null){
          // 에러페이지 or 예외처리?
        }
        return "redirect:/board/home";
    }
}
