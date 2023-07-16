package com.trellolike.model.repository;

import com.trellolike.model.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String>  {

    @Query(value = "select b.* from Board b join User_Board ub on b.id_board = ub.id_board where ub.id_user = :id_user", nativeQuery = true)
    List<Board> getBoardsByUserId(@Param("id_user") Integer id);
}
