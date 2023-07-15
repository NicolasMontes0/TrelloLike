package com.trellolike.model.repository;

import com.trellolike.model.model.User_Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<User_Board, String>  {
}
