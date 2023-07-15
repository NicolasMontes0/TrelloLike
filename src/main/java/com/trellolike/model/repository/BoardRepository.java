package com.trellolike.model.repository;

import com.trellolike.model.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String>  {
}
