package com.trellolike.model.service.Interface;

import com.trellolike.model.model.Board;

import java.util.List;

public interface BoardService {

    List<Board> getAll();

    Board get(Integer id);

    Board add(Board board, Integer id_user);

    Board update(Integer id, Board board);

    void remove(Integer id);

    List<Board> getByUserId(Integer id);
}
