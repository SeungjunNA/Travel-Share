package com.cloudtrack.project.repository;

import com.cloudtrack.project.Entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BoardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Board> boardRowMapper() {
        return(rs, rowNum) -> {
            return Board.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .build();
        };
    }

    public List<Board> findAll(){
        String sql = "select * from board order by id desc";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    public int findBoardId(String title){
        String sql = "select id from board where title = ?";
        int n = jdbcTemplate.queryForObject(sql, Integer.class, title);
        System.out.println(n);
        return n;
    }

    public Board findByBoardTitle(String title){
        String sql = "select * from board where title = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {title}, boardRowMapper());
    }

    public Board create(Board board) {
        String sql = "insert into board (title, description) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getDescription());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if(key == null) return board;
        return board.toBuilder()
                .id(key.longValue())
                .build();
    }
}
