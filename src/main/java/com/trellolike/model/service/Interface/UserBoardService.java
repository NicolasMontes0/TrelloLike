package com.trellolike.model.service.Interface;

import com.trellolike.model.model.User_Board;

import java.util.List;

public interface UserBoardService {

    List<User_Board> getAll();

    User_Board get(Integer id);

    User_Board add(User_Board user_board);

    void remove(Integer id);
}
