package com.cloudtrack.project.service;

import com.cloudtrack.project.entity.Board;
import com.cloudtrack.project.dto.BoardDto;
import com.cloudtrack.project.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board create(BoardDto boardDto){
        return boardRepository.create(boardDto.toEntity());
    }

    public List<Board> findAll(){
        return boardRepository.findAll();
    }
    public Board findById(long boardId){
        return boardRepository.findById(boardId);
    }

    public long update(BoardDto boardDto){
        Board board = boardRepository.findById(boardDto.getId());
        board.setTitle(boardDto.getTitle());
        board.setDescription(boardDto.getDescription());
        Board savedBoard = boardRepository.update(board);
        return savedBoard.getId();
    }
    public void delete(long boardId){
        boardRepository.delete(boardId);
    }
}
