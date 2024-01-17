package com.cloudtrack.project.controller;

import com.cloudtrack.project.entity.Board;
import com.cloudtrack.project.dto.BoardDto;
import com.cloudtrack.project.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/edit-form/{boardId}")
    public String update(@PathVariable(name = "boardId") long boardId, Model model){
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "board-edit-form";
    }

    @PostMapping("/create-board")
    public String createBoard(@ModelAttribute(name = "boardDto") BoardDto boardDto){
        Board board = boardService.create(boardDto);
        if(board == null){
          // 에러페이지 or 예외처리?
        }
        return "redirect:/board/home";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "boardDto") BoardDto boardDto){
        long boardId = boardService.update(boardDto);
        return "redirect:/travel/" + boardId;
    }

    @DeleteMapping("/delete/{boardId}")
    public String delete(@PathVariable(name = "boardId") long boardId){
        boardService.delete(boardId);
        return "redirect:/board/home";
    }
}
